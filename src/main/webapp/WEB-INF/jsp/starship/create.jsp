<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="regula" %>
<html>
  <head>
      <title>Let's make a Starship!</title>
      <script type="text/javascript" src="/js/regula/regula.js"></script>
      <script type="text/javascript" src="/js/jquery/jquery-1.4.2.min.js"></script>
      <script type="text/javascript">
        jQuery(document).ready(function() {
            regula.custom({
                name: "RegistryNumber",
                formSpecific: false,
                params: [],
                defaultMessage: "A registry number needs to be of the form NCC-1701, NX-1701, or NCC-1701-E.",
                validator: function(params) {
                   return /^N(CC|X)-[0-9]+(-[A-Z])?$/.test(this.value);
                }
            });

            regula.custom({
                name: "Stardate",
                formSpecific: false,
                params: [],
                defaultMessage: "This is not a valid stardate.",
                validator: function(params) {
                   return /^[0-9]+\.[0-9]$/.test(this.value);
                }
            });
        });
      </script>
  </head>
  <body>

  <form:form commandName="starship">
      <table>
          <tr>
              <td>Starship Name:</td>
              <td><form:input path="shipName" /></td>
              <td><form:errors path="shipName" /></td>
          </tr>
          <tr>
              <td>Registry:</td>
              <td><form:input path="registry" /></td>
              <td><form:errors path="registry" /></td>
          </tr>
           <tr>
              <td>Model:</td>
              <td><form:input path="model" /></td>
              <td><form:errors path="model" /></td>
          </tr>
          <tr>
              <td>Launch Date:</td>
              <td><form:input path="launchDate" /></td>
              <td><form:errors path="launchDate" /></td>
          </tr>
          <tr>
              <td>Maximum Warp:</td>
              <td><form:input path="maximumWarp" /></td>
              <td><form:errors path="maximumWarp" /></td>
          </tr>
          <tr>
              <td>Shield Frequency:</td>
              <td><form:input path="shieldFrequency" /></td>
              <td><form:errors path="shieldFrequency" /></td>
          </tr>
          <tr>
              <td>Crew:</td>
              <td><form:input path="crew" /></td>
              <td><form:errors path="crew" /></td>
          </tr>
          <tr>
              <td>Captain's Name:</td>
              <td><form:input path="captain.name" /></td>
              <td><form:errors path="captain.name" /></td>
          </tr>
          <tr>
              <td>Captain's Rank:</td>
              <td><form:input path="captain.rank" /></td>
              <td><form:errors path="captain.rank" /></td>
          </tr>
          <tr>
              <td>Captain's Age:</td>
              <td><form:input path="captain.age" /></td>
              <td><form:errors path="captain.age" /></td>
          </tr>
          <tr>
              <td>Captain's Service Number:</td>
              <td><form:input path="captain.serviceNumber" /></td>
              <td><form:errors path="captain.serviceNumber" /></td>
          </tr>
          <tr>
              <td>Street:</td>
              <td><form:input path="captain.address.street" /></td>
              <td><form:errors path="captain.address.street" /></td>
          </tr>
          <tr>
              <td>City:</td>
              <td><form:input path="captain.address.city" /></td>
              <td><form:errors path="captain.address.city" /></td>
          </tr>
          <tr>
              <td>State:</td>
              <td><form:input path="captain.address.state" /></td>
              <td><form:errors path="captain.address.state" /></td>
          </tr>
          <tr>
              <td>Zip:</td>
              <td><form:input path="captain.address.zip" /></td>
              <td><form:errors path="captain.address.zip" /></td>
          </tr>
          <tr>
              <td>Country:</td>
              <td><form:input path="captain.address.country" /></td>
              <td><form:errors path="captain.address.country" /></td>
          </tr>
          <tr>
              <td colspan="3">
                  <input type="submit" value="Create!" />
              </td>
          </tr>

      </table>
  </form:form>

  <script type="text/javascript">
        jQuery(document).ready(function() {
            jQuery("form").live("submit", function() {

                jQuery("span[id$=_error]").remove();

                var validationResults = regula.validate();

                for(var i = 0; i < validationResults.length; i++) {
                    var validationResult = validationResults[i];

                    for(var j = 0; j < validationResult.failingElements.length; j++) {
                        var failingElement = validationResult.failingElements[j];
                        var elementId = failingElement.id;
                        var $element = jQuery("#" + elementId.replace(/\./g, "\\."));

                        $element.after("<span id=\"" + elementId.replace(/\./g, "\\.") + "_error\" style=\"color:#ff0000\">" + $element.text() + " " + validationResult.message + "</span>");
                    }
                }

                return (validationResults.length == 0);
            });
        });
  </script>

  <regula:generate-validation />

  </body>
</html>
