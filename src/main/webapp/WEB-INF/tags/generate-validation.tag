<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="string" uri="http://regula-string" %>


<c:if test="${not empty propertyToConstraintInstancesMap or propertyToConstraintInstancesMap != null}">
   <script type="text/javascript">
       jQuery(document).ready(function() {

           function generateRegulaConstraint(constraintName, params) {
               var constraintDefinitionString = "";
               var parameterString = "";

               for(var i = 0; i < params.length; i++) {
                  var param = params[i];
                  /*
                   There are special cases that we handle here:

                    o If the parameter is a message parameter and of the form {fully.qualified.name} then we ignore it
                    o We look at the type property of the parameter and declare our parameter appropriately
                  */

                  if(param.type != "String") {
                     //if the parameter value is not a String type, then we can simply use param=value
                     //UNLESS the parameter is a groups parameter and it is an empty array "[]"
                     if(param.name != "groups" || param.name != "[]") {
                        parameterString += (param.name + "=" + param.value + ", ");
                     }
                  }

                  else {
                     //if parameter value is a String type, we can simply use param="value" EXCEPT if it is
                     //a regular expression
                     if(constraintName == "Pattern" && param.name == "regexp") {
                        parameterString += (param.name + "=" + param.value + ", ");
                     }

                     else {
                        parameterString += (param.name + "=\"" + param.value +"\", ");
                     }
                  }
               }

               parameterString = parameterString.replace(/, $/, "");

               if(parameterString == "") {
                  constraintDefinitionString = "@" + constraintName;
               }

               else {
                  constraintDefinitionString = "@" + constraintName + "(" + parameterString + ")";
               }

               return constraintDefinitionString;
           }

           var $element = null;
           var dataConstraintsAttribute = "";
           var fieldConstraints = "";
           var constraintName = "";
           var params = null;

           <c:forEach items="${propertyToConstraintInstancesMap}" var="mapEntry">

               <c:set var="fieldName" value="${mapEntry.key}" />
               $element = jQuery("[name=${fieldName}]");
               dataConstraintsAttribute = $element.attr("data-constraints");
               fieldConstraints = "";

               <c:forEach items="${mapEntry.value}" var="validationConstraint">
                   constraintName = "${validationConstraint.name}";

                   params = [];

                   <%-- this insanity here is for TRIPLE escaping. JSTL -> Javascript -> HTML Attribute. If we only do a
                        double escape we get valid Javascript, but since we're inserting this into an HTML attribute,
                        we need to escape it once more --%>

                   <c:set var="search" value="\"" />
                   <c:set var="replace" value="\\\\\\\"" />

                   <c:forEach items="${validationConstraint.parameters}" var="parameter">
                       <c:set var="parameterValue" value="${parameter.value}" />

                       <c:if test="${parameter.name == 'message'}">

                           <fmt:message key="${parameterValue}" var="parameterValue" />

                           <c:if test="${!fn:endsWith(parameterValue, '.')}">
                               <c:set var="parameterValue" value="${parameterValue}." />
                           </c:if>
                       </c:if>
                       params.push({name: "${parameter.name}", type: "${parameter.type}", value: "${fn:replace(parameterValue, search, replace)}"});
                   </c:forEach>

                   fieldConstraints += " " + generateRegulaConstraint(constraintName, params);
               </c:forEach>

               if(dataConstraintsAttribute) {
                  $element.attr("data-constraints", dataConstraintsAttribute + fieldConstraints);
               }

               else {
                  $element.attr("data-constraints", fieldConstraints);
               }

               $element.addClass("regula-validation");
           </c:forEach>

           var compoundConstraintDefinitions = {};
           var compoundConstraintDefinition = {};
           var composingConstraints = [];
           var composingConstraint = {};
           var composingConstraintParameters = [];
           var composingConstraintParameter = {};

           <c:forEach items="${compoundConstraintDefinitionSet}" var="compoundConstraintDefinition">
               <c:set var="name" value="${compoundConstraintDefinition.name}" />

               <c:set var="parameters" value="" />

               <c:forEach items="${compoundConstraintDefinition.parameters}" var="parameter">
                   <c:set var="parameters" value="${parameters}\"${parameter}\"," />
               </c:forEach>

               <c:set var="parameters" value="${string:replaceUsingRegex(',$', '', parameters, '')}" />

               compoundConstraintDefinition = {};

               compoundConstraintDefinition["params"] = [${parameters}];

               composingConstraints = [];

               <c:forEach items="${compoundConstraintDefinition.composingConstraints}" var="composingConstraint">

                   composingConstraint = {};
                   composingConstraint["name"] = "${composingConstraint.name}";
                   composingConstraintParameters = {};

                   <c:forEach items="${composingConstraint.parameters}" var="composingConstraintParameter">

                       composingConstraintParameters["name"] = "${composingConstraintParameter.name}";

                       <c:choose>
                           <c:when test="${composingConstraintParameter.type != 'String'}">
                               <c:choose>
                                   <c:when test="${composingConstraintParameter.type == 'Array'}">
                                       <c:set var="value" value="${fn:replace(composingConstraintParameter.value, '[', '')}" />
                                       <c:set var="value" value="${fn:replace(value, ']', '')}" />
                                       <c:set var="groups" value="" />

                                       <c:forEach items="${fn:split(value, ',')}" var="group">
                                           <c:set var="groups" value="${groups}regula.Group.${group}," />
                                       </c:forEach>

                                       <c:set var="groups" value="${string:replaceUsingRegex(',$', '', groups, '')}" />

                                       composingConstraintParameters["${composingConstraintParameter.name}"] = [${groups}];
                                   </c:when>
                                   <c:otherwise>
                                       composingConstraintParameters["${composingConstraintParameter.name}"] = ${composingConstraintParameter.value};
                                   </c:otherwise>
                               </c:choose>

                           </c:when>
                           <c:otherwise>
                               composingConstraintParameters["${composingConstraintParameter.name}"] = "${composingConstraintParameter.value}";
                           </c:otherwise>
                       </c:choose>

                   </c:forEach>

                   composingConstraint["params"] = composingConstraintParameters;

                   composingConstraints.push(composingConstraint);

               </c:forEach>

               compoundConstraintDefinition["constraints"] = composingConstraints;

               compoundConstraintDefinitions["${name}"] = compoundConstraintDefinition;


           </c:forEach>

           /*
             our compound constraints come in no particular order, and may have dependencies between them. This means that
             if we first encounter a constraint that has composing constraints that have NOT been defined, we have to define
             them first. A recursive algorithm is perfect for this
           */

           for(var compoundConstraintName in compoundConstraintDefinitions) {
              if(compoundConstraintDefinitions.hasOwnProperty(compoundConstraintName)) {

                 /* recursive self-invoked anonymous function that traverses the "dependency" tree */
                 (function(definition, name) {
                    if(typeof regula.Constraint[name] == "undefined") {

                       var constraints = [];

                       for(var i = 0; i < definition.constraints.length; i++) {

                          var constraint = definition.constraints[i];

                          if(typeof regula.Constraint[constraint.name] == "undefined") {
                             arguments.callee(compoundConstraintDefinitions[constraint.name], constraint.name);
                          }

                          var composingConstraint = {constraintType: regula.Constraint[constraint.name]};

                          if(typeof constraint.params != "undefined") {
                             composingConstraint.params = constraint.params;
                          }

                          constraints.push(composingConstraint);
                       }

                       regula.compound({name: name, constraints: constraints});
                    }
                 }(compoundConstraintDefinitions[compoundConstraintName], compoundConstraintName));
              }
           }
       });

       jQuery(document).ready(function() {
           regula.bind();
       });

       jQuery(document).ready(function() {
           jQuery("form").live("submit", function() {

               jQuery(this).find(".inf-error").each(function() {
                   jQuery(this).text("");
               });

               var validationResults = regula.validate();

               for(var i = 0; i < validationResults.length; i++) {
                   var validationResult = validationResults[i];

                   for(var j = 0; j < validationResult.failingElements.length; j++) {
                       var failingElement = validationResult.failingElements[j];
                       var elementId = failingElement.id;
                       var $element = jQuery("#" + elementId + "_error");

                       $element.text($element.text() + " " + validationResult.message).css("color", "#ff0000");
                   }
               }

               return (validationResults.length == 0);
           });
       });

       <c:set var="calledOnce" value="true" scope="page" />
   </script>
</c:if>