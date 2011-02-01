package net.vivin.validation;

import net.vivin.validation.constraint.ConstraintDefinition;
import net.vivin.validation.constraint.ConstraintInstance;
import net.vivin.validation.constraint.ConstraintParameter;
import net.vivin.validation.service.*;
import net.vivin.validation.service.testcaseclass.*;
import net.vivin.validation.service.testcaseconstraint.*;

import java.util.HashSet;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;

import org.apache.commons.lang.StringUtils;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.*;

@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TestValidationConstraintsService extends AbstractTestNGSpringContextTests {

    @Autowired
    ValidationConstraintsService validationConstraintsService;

    @Autowired
    private LocalValidatorFactoryBean validator;

    private Map<String, Set<ConstraintInstance>> propertyToConstraintInstancesMap;
    private Set<ConstraintDefinition> compoundConstraintDefinitionSet;

    private final int numDefaultParameters = 3; //includes groups, label, and message

    @Test
    public void TestEmptyPojo() {
        getValidationInformation(EmptyPojo.class);

        assertEquals(propertyToConstraintInstancesMap.keySet().size(), 0, "Empty POJO must not have any constraints");
        assertEquals(compoundConstraintDefinitionSet.size(), 0, "Empty POJO must not have any compound constraints");
    }

    @Test
    public void TestPojoWithOneConstraint() {
        getValidationInformation(PojoWithOneConstraint.class);

        assertEquals(propertyToConstraintInstancesMap.keySet().size(), 1, "POJO must only have one constraint");
        assertEquals(compoundConstraintDefinitionSet.size(), 0, "POJO must not have any compound constraints");

        for(Set constraintInstances : propertyToConstraintInstancesMap.values()) {
            assertEquals(constraintInstances.size(), 1, "Each property must have only one constraint bound to it");
        }
    }

    @Test
    public void TestPojoWithConstraints() {
        getValidationInformation(PojoWithConstraints.class);

        BeanDescriptor classDescriptor = validator.getConstraintsForClass(PojoWithConstraints.class);
        int numConstrainedProperties = classDescriptor.getConstrainedProperties().size();

        assertEquals(propertyToConstraintInstancesMap.keySet().size(), numConstrainedProperties, "All properties in POJO are constrained, but number of constrained properties does not match");
        assertEquals(propertyToConstraintInstancesMap.values().size(), numConstrainedProperties, "All properties in POJO are constrained (one constraint per property), but number of constraints does not match number of properties with constraints");
        assertEquals(compoundConstraintDefinitionSet.size(), 0, "POJO must not have any compound constraints");

        for(Set constraintInstances : propertyToConstraintInstancesMap.values()) {
            assertEquals(constraintInstances.size(), 1, "Each property must have only one constraint bound to it");
        }
    }

    @Test
    public void TestPojoWithOneClassLevelConstraint() {
        testClassLevelConstraints(PojoWithOneClassLevelConstraint.class, false);

        Set<ConstraintInstance> constraintSet = propertyToConstraintInstancesMap.get(StringUtils.uncapitalize(PojoWithOneClassLevelConstraint.class.getSimpleName()));

        ConstraintInstance constraintInstance = (ConstraintInstance) constraintSet.toArray()[0];
        assertEquals(constraintInstance.getName(), ClassLevelConstraint.class.getSimpleName(), "Unexpected constraint bound to class");
        assertTrue(constraintInstance.isClassLevelConstraint(), "Must be marked as a class-level constraint");
    }

    @Test
    public void TestPojoWithClassLevelConstraints() {
        testClassLevelConstraints(PojoWithClassLevelConstraints.class, false);

        Set<ConstraintInstance> constraintSet = propertyToConstraintInstancesMap.get(StringUtils.uncapitalize(PojoWithClassLevelConstraints.class.getSimpleName()));
        Set<String> constraintNameSet = new HashSet<String>();

        ConstraintInstance firstConstraintInstance = (ConstraintInstance) constraintSet.toArray()[0];
        ConstraintInstance secondConstraintInstance = (ConstraintInstance) constraintSet.toArray()[1];

        constraintNameSet.add(firstConstraintInstance.getName());
        constraintNameSet.add(secondConstraintInstance.getName());

        assertTrue(constraintNameSet.contains(ClassLevelConstraint.class.getSimpleName()), "ClassLevelConstraint must be bound to class");
        assertTrue(firstConstraintInstance.isClassLevelConstraint(), "Must be marked as a class-level constraint");

        assertTrue(constraintNameSet.contains(AnotherClassLevelConstraint.class.getSimpleName()), "AnotherClassLevelConstraint must be bound to class");
        assertTrue(secondConstraintInstance.isClassLevelConstraint(), "Must be marked as a class-level constraint");
    }

    @Test
    public void TestPojoWithOneClassLevelConstraintAndOnePropertyConstraint() {
        testClassLevelAndPropertyConstraints(PojoWithOneClassLevelConstraintAndOnePropertyConstraint.class);

        Set<ConstraintInstance> constraintSet = propertyToConstraintInstancesMap.get(StringUtils.uncapitalize(PojoWithOneClassLevelConstraintAndOnePropertyConstraint.class.getSimpleName()));

        ConstraintInstance firstConstraintInstance = (ConstraintInstance) constraintSet.toArray()[0];
        assertEquals(firstConstraintInstance.getName(), ClassLevelConstraint.class.getSimpleName(), "Unexpected constraint bound to class");
        assertTrue(firstConstraintInstance.isClassLevelConstraint(), "Must be marked as a class-level constraint");

        for(String propertyName : propertyToConstraintInstancesMap.keySet()) {
            if(!propertyName.equals(StringUtils.uncapitalize(PojoWithClassLevelConstraints.class.getSimpleName()))) {
                assertEquals(propertyToConstraintInstancesMap.get(propertyName).size(), 1, "Each property must only have one constraint bound to it");
            }
        }
    }

    @Test
    public void TestPojoWithClassLevelAndPropertyConstraints() {
        testClassLevelAndPropertyConstraints(PojoWithClassLevelAndPropertyConstraints.class);

        Set<ConstraintInstance> constraintSet = propertyToConstraintInstancesMap.get(StringUtils.uncapitalize(PojoWithClassLevelAndPropertyConstraints.class.getSimpleName()));
        Set<String> constraintNameSet = new HashSet<String>();

        ConstraintInstance firstConstraintInstance = (ConstraintInstance) constraintSet.toArray()[0];
        ConstraintInstance secondConstraintInstance = (ConstraintInstance) constraintSet.toArray()[1];

        constraintNameSet.add(firstConstraintInstance.getName());
        constraintNameSet.add(secondConstraintInstance.getName());

        assertTrue(constraintNameSet.contains(ClassLevelConstraint.class.getSimpleName()), "ClassLevelConstraint must be bound to class");
        assertTrue(firstConstraintInstance.isClassLevelConstraint(), "Must be marked as a class-level constraint");

        assertTrue(constraintNameSet.contains(AnotherClassLevelConstraint.class.getSimpleName()), "AnotherClassLevelConstraint must be bound to class");
        assertTrue(secondConstraintInstance.isClassLevelConstraint(), "Must be marked as a class-level constraint");

        for(String propertyName : propertyToConstraintInstancesMap.keySet()) {
            if(!propertyName.equals(StringUtils.uncapitalize(PojoWithClassLevelAndPropertyConstraints.class.getSimpleName()))) {
                assertEquals(propertyToConstraintInstancesMap.get(propertyName).size(), 1, "Each property must only have one constraint bound to it");
            }
        }
    }

    @Test
    public void TestConstraintsHaveCorrectParameters() {
        getValidationInformation(PojoWithConstraints.class);

        for(Set constraintInstances : propertyToConstraintInstancesMap.values()) {
            assertEquals(constraintInstances.size(), 1, "Each property must have only one constraint bound to it");
        }

        Set<ConstraintInstance> constraintInstances = propertyToConstraintInstancesMap.get("intField");
        ConstraintInstance constraintInstance = (ConstraintInstance) constraintInstances.toArray()[0];

        assertEquals(constraintInstance.getName(), Min.class.getSimpleName(), "Unexpected constraint on field");
        assertEquals(constraintInstance.getParameters().size(), numDefaultParameters + 1, "Unexpected number of parameters on constraint " + constraintInstance.getName());
        for(ConstraintParameter parameter : constraintInstance.getParameters()) {

            if(parameter.getName().equals("value")) {
                testParameterValue(parameter.getValue(), Integer.toString(ConstraintArgumentValues.MIN_VALUE), constraintInstance.getName(), parameter.getName());
            }

            else if(parameter.getName().equals("message")) {
                testParameterValue(parameter.getValue(), ConstraintArgumentValues.MESSAGE, constraintInstance.getName(), parameter.getName());
            }

            else if(parameter.getName().equals("groups")) {
                testParameterValue(parameter.getValue(), "[" + Default.class.getSimpleName() + "]", constraintInstance.getName(), parameter.getName());
            }

            else if(parameter.getName().equals("label")) {
                testParameterValue(parameter.getValue(), getFriendlyNameForProperty("intField"), constraintInstance.getName(), parameter.getName());
            }
        }

        constraintInstances = propertyToConstraintInstancesMap.get("floatField");
        constraintInstance = (ConstraintInstance) constraintInstances.toArray()[0];

        assertEquals(constraintInstance.getName(), Max.class.getSimpleName(), "Unexpected constraint on field");
        assertEquals(constraintInstance.getParameters().size(), numDefaultParameters + 1, "Unexpected number of parameters on constraint " + constraintInstance.getName());
        for(ConstraintParameter parameter : constraintInstance.getParameters()) {

            if(parameter.getName().equals("value")) {
                testParameterValue(parameter.getValue(), Integer.toString(ConstraintArgumentValues.MAX_VALUE), constraintInstance.getName(), parameter.getName());
            }

            else if(parameter.getName().equals("groups")) {
                testParameterValue(parameter.getValue(), "[" + Default.class.getSimpleName() + "]", constraintInstance.getName(), parameter.getName());
            }

            else if(parameter.getName().equals("label")) {
                testParameterValue(parameter.getValue(), getFriendlyNameForProperty("floatField"), constraintInstance.getName(), parameter.getName());
            }
        }

        constraintInstances = propertyToConstraintInstancesMap.get("doubleField");
        constraintInstance = (ConstraintInstance) constraintInstances.toArray()[0];

        assertEquals(constraintInstance.getName(), Range.class.getSimpleName(), "Unexpected constraint on field");
        assertEquals(constraintInstance.getParameters().size(), numDefaultParameters + 2, "Unexpected number of parameters on constraint " + constraintInstance.getName());
        for(ConstraintParameter parameter : constraintInstance.getParameters()) {

            if(parameter.getName().equals("min")) {
                testParameterValue(parameter.getValue(), Integer.toString(ConstraintArgumentValues.MIN_VALUE), constraintInstance.getName(), parameter.getName());
            }

            else if(parameter.getName().equals("max")) {
                testParameterValue(parameter.getValue(), Integer.toString(ConstraintArgumentValues.MAX_VALUE), constraintInstance.getName(), parameter.getName());
            }

            else if(parameter.getName().equals("groups")) {
                testParameterValue(parameter.getValue(), "[" + Default.class.getSimpleName() + "]", constraintInstance.getName(), parameter.getName());
            }

            else if(parameter.getName().equals("label")) {
                testParameterValue(parameter.getValue(), getFriendlyNameForProperty("doubleField"), constraintInstance.getName(), parameter.getName());
            }
        }

        constraintInstances = propertyToConstraintInstancesMap.get("stringField");
        constraintInstance = (ConstraintInstance) constraintInstances.toArray()[0];

        assertEquals(constraintInstance.getName(), Pattern.class.getSimpleName(), "Unexpected constraint on field");
        assertEquals(constraintInstance.getParameters().size(), numDefaultParameters + 2, "Unexpected number of parameters on constraint " + constraintInstance.getName());
        for(ConstraintParameter parameter : constraintInstance.getParameters()) {

            if(parameter.getName().equals("regexp")) {
                testParameterValue(parameter.getValue(), ConstraintArgumentValues.REGEXP, constraintInstance.getName(), parameter.getName());
            }

            else if(parameter.getName().equals("flags")) {
                testParameterValue(parameter.getValue(), ConstraintArgumentValues.FLAGS_JS_EQUIVALENT, constraintInstance.getName(), parameter.getName());
            }

            else if(parameter.getName().equals("groups")) {
                testParameterValue(parameter.getValue(), "[" + Default.class.getSimpleName() + "]", constraintInstance.getName(), parameter.getName());
            }

            else if(parameter.getName().equals("label")) {
                testParameterValue(parameter.getValue(), getFriendlyNameForProperty("stringField"), constraintInstance.getName(), parameter.getName());
            }
        }

        constraintInstances = propertyToConstraintInstancesMap.get("booleanField");
        constraintInstance = (ConstraintInstance) constraintInstances.toArray()[0];

        assertEquals(constraintInstance.getName(), NotBlank.class.getSimpleName(), "Unexpected constraint on field");
        assertEquals(constraintInstance.getParameters().size(), numDefaultParameters, "Unexpected number of parameters on constraint " + constraintInstance.getName());
        for(ConstraintParameter parameter : constraintInstance.getParameters()) {

            if(parameter.getName().equals("groups")) {
                testParameterValue(parameter.getValue(), "[" + FirstGroup.class.getSimpleName() + "," + SecondGroup.class.getSimpleName() + "]", constraintInstance.getName(), parameter.getName());
            }
        }

    }

    @Test
    public void TestPojoWithOneCascadedConstraint() {
        getValidationInformation(PojoWithOneCascadedConstraint.class);

        BeanDescriptor classDescriptor = validator.getConstraintsForClass(PojoWithConstraints.class);
        int numConstrainedProperties = classDescriptor.getConstrainedProperties().size();

        assertEquals(propertyToConstraintInstancesMap.keySet().size(), numConstrainedProperties, "There must be an entry for each constrained property in the class that represents the type of the cascaded property");
        assertEquals(propertyToConstraintInstancesMap.values().size(), numConstrainedProperties, "All properties in the class that represents the cascaded property are constrained (one constraint per property), but number of constraints does not match number of properties with constraints");
        assertEquals(compoundConstraintDefinitionSet.size(), 0, "POJO must not have any compound constraints");

        for(Set constraintInstances : propertyToConstraintInstancesMap.values()) {
            assertEquals(constraintInstances.size(), 1, "Each property must have only one constraint bound to it");
        }

        for(String propertyName : propertyToConstraintInstancesMap.keySet()) {
            assertTrue(propertyName.startsWith("pojoWithConstraints."), "Each property must start with the name of the cascaded property in the parent class");
        }
    }

    @Test
    public void TestPojoWithCascadedConstraints() {
        getValidationInformation(PojoWithCascadedConstraints.class);

        BeanDescriptor classDescriptor = validator.getConstraintsForClass(PojoWithConstraints.class);
        int numConstrainedProperties = classDescriptor.getConstrainedProperties().size();

        assertEquals(propertyToConstraintInstancesMap.keySet().size(), numConstrainedProperties * 2, "There must be an entry for each constrained property in the class that represents the type of the cascaded property");
        assertEquals(propertyToConstraintInstancesMap.values().size(), numConstrainedProperties * 2, "All properties in the class that represents the cascaded property are constrained (one constraint per property), but number of constraints does not match number of properties with constraints");
        assertEquals(compoundConstraintDefinitionSet.size(), 0, "POJO must not have any compound constraints");

        for(Set constraintInstances : propertyToConstraintInstancesMap.values()) {
            assertEquals(constraintInstances.size(), 1, "Each property must have only one constraint bound to it");
        }

        for(String propertyName : propertyToConstraintInstancesMap.keySet()) {
            assertTrue(propertyName.startsWith("firstPojoWithConstraints.") || propertyName.startsWith("secondPojoWithConstraints."), "Each property must be prefixed by the path to itself");
        }
    }

    @Test
    public void TestPojoWithCascadedAndNonCascadedConstraints() {
        getValidationInformation(PojoWithCascadedAndNonCascadedConstraints.class);

        BeanDescriptor classDescriptor = validator.getConstraintsForClass(PojoWithConstraints.class);
        int numConstrainedProperties = classDescriptor.getConstrainedProperties().size();

        //+2 for the non-cascaded properties
        assertEquals(propertyToConstraintInstancesMap.keySet().size(), (numConstrainedProperties * 2) + 2, "There must be an entry for each constrained property in the class that represents the type of the cascaded property and also for each non-cascaded entry in the parent class");
        assertEquals(propertyToConstraintInstancesMap.values().size(), (numConstrainedProperties * 2) + 2, "All properties in the class that represents the cascaded property are constrained (one constraint per property) and there are also non-cascaded properties in the parent class, but number of constraints does not match number of properties with constraints");
        assertEquals(compoundConstraintDefinitionSet.size(), 0, "POJO must not have any compound constraints");

        for(Set constraintInstances : propertyToConstraintInstancesMap.values()) {
            assertEquals(constraintInstances.size(), 1, "Each property must have only one constraint bound to it");
        }

        for(String propertyName : propertyToConstraintInstancesMap.keySet()) {
            if(!propertyName.equals("stringField") && !propertyName.equals("intField")) {
                assertTrue(propertyName.startsWith("firstPojoWithConstraints.") || propertyName.startsWith("secondPojoWithConstraints."), "Each property name must be prefixed by the path to itself");
            }
        }
    }

    @Test
    public void TestPojoWithNestedCascadedConstraint() {
        getValidationInformation(ToplevelPojoWithCascadedConstraint.class);

        BeanDescriptor classDescriptor = validator.getConstraintsForClass(PojoWithConstraints.class);
        int numConstrainedProperties = classDescriptor.getConstrainedProperties().size();

        assertEquals(propertyToConstraintInstancesMap.keySet().size(), numConstrainedProperties, "There must be an entry for each constrained property in the class that represents the type of the cascaded property");
        assertEquals(propertyToConstraintInstancesMap.values().size(), numConstrainedProperties, "All properties in the class that represents the cascaded property are constrained (one constraint per property), but number of constraints does not match number of properties with constraints");
        assertEquals(compoundConstraintDefinitionSet.size(), 0, "POJO must not have any compound constraints");

        for(Set constraintInstances : propertyToConstraintInstancesMap.values()) {
            assertEquals(constraintInstances.size(), 1, "Each property must have only one constraint bound to it");
        }

        for(String propertyName : propertyToConstraintInstancesMap.keySet()) {
            assertTrue(propertyName.startsWith("pojoWithOneCascadedConstraint.pojoWithConstraints."), "Each property name must be prefixed by the path to itself");
        }
    }

    @Test
    public void TestPojoWithClassLevelCompoundConstraint() {
        testClassLevelConstraints(PojoWithClassLevelCompoundConstraint.class, true);

        Set<ConstraintInstance> constraintSet = propertyToConstraintInstancesMap.get(StringUtils.uncapitalize(PojoWithClassLevelCompoundConstraint.class.getSimpleName()));

        ConstraintInstance constraintInstance = (ConstraintInstance) constraintSet.toArray()[0];
        assertEquals(constraintInstance.getName(), GreatGreatGrandParentConstraint.class.getSimpleName(), "Unexpected constraint bound to class");
        assertTrue(constraintInstance.isClassLevelConstraint(), "Must be marked as a class-level constraint");

        testCompoundConstraints();
    }

    @Test
    public void TestPojoWithCompoundConstraint() {
        getValidationInformation(PojoWithCompoundConstraint.class);

        BeanDescriptor classDescriptor = validator.getConstraintsForClass(PojoWithCompoundConstraint.class);
        int numConstrainedProperties = classDescriptor.getConstrainedProperties().size();

        assertEquals(propertyToConstraintInstancesMap.keySet().size(), numConstrainedProperties, "POJO must have only two properties that are bound to a constraint");
        assertEquals(propertyToConstraintInstancesMap.values().size(), numConstrainedProperties, "POJO must have two constraints that have been bound");

        testCompoundConstraints();
    }

    private void testCompoundConstraints() {
        for(Set constraintInstance : propertyToConstraintInstancesMap.values()) {
            assertEquals(constraintInstance.size(), 1, "POJO must have one compound-constraint definition per property");
        }

        assertEquals(compoundConstraintDefinitionSet.size(), 6, "POJO must have six compound-constraint definitions");

        for(ConstraintDefinition constraintDefinition : compoundConstraintDefinitionSet) {
            if(constraintDefinition.getName().equals(GreatGreatGrandParentConstraint.class.getSimpleName())) {

                boolean found = false;
                for(String parameterName : constraintDefinition.getParameters()) {
                    if(!found) {
                        found = parameterName.equals("aParameter");
                    }
                }

                assertTrue(constraintDefinition.isReportAsSingleViolation(), GreatGreatGrandParentConstraint.class.getSimpleName() + " is expected to report violations as a single violation");

                assertTrue(found, "A required parameter was not found on the parameter definition for " + GreatGreatGrandParentConstraint.class.getSimpleName());

                assertEquals(constraintDefinition.getComposingConstraints().size(), 1, constraintDefinition.getName() + " must have one composing constraint");

                ConstraintInstance constraintInstance = (ConstraintInstance) constraintDefinition.getComposingConstraints().toArray()[0];

                assertEquals(constraintInstance.getName(), GreatGrandParentConstraint.class.getSimpleName(), "Unexpected composing constraint found");

                for(ConstraintParameter parameter : constraintInstance.getParameters()) {
                    if(parameter.getName().equals("value")) {
                        testParameterValue(parameter.getValue(), Integer.toString(ConstraintArgumentValues.GREAT_GRANDPARENT_CONSTRAINT_VALUE), constraintInstance.getName(), parameter.getName());
                    }
                }
            }

            else if(constraintDefinition.getName().equals(GreatGrandParentConstraint.class.getSimpleName())) {
                assertEquals(constraintDefinition.getComposingConstraints().size(), 1, constraintDefinition.getName() + " must have one composing constraint");

                ConstraintInstance constraintInstance = (ConstraintInstance) constraintDefinition.getComposingConstraints().toArray()[0];

                assertEquals(constraintInstance.getName(), GrandParentConstraint.class.getSimpleName(), "Unexpected composing constraint found");

                for(ConstraintParameter parameter : constraintInstance.getParameters()) {
                    if(parameter.getName().equals("value")) {
                        testParameterValue(parameter.getValue(), Integer.toString(ConstraintArgumentValues.GRANDPARENT_CONSTRAINT_VALUE), constraintInstance.getName(), parameter.getName());
                    }
                }
            }

            else if(constraintDefinition.getName().equals(GrandParentConstraint.class.getSimpleName())) {
                assertEquals(constraintDefinition.getComposingConstraints().size(), 2, constraintDefinition.getName() + " must have composing constraints");

                Set<String> constraintNames = new HashSet<String>();
                ConstraintInstance firstConstraintInstance = (ConstraintInstance) constraintDefinition.getComposingConstraints().toArray()[0];
                ConstraintInstance secondConstraintInstance = (ConstraintInstance) constraintDefinition.getComposingConstraints().toArray()[1];

                constraintNames.add(firstConstraintInstance.getName());
                constraintNames.add(secondConstraintInstance.getName());

                assertTrue(constraintNames.contains(ParentConstraint.class.getSimpleName()), "ParentConstraint must be present");
                assertTrue(constraintNames.contains(ParentSiblingConstraint.class.getSimpleName()), "ParentSiblingConstraint must be present");

                ConstraintInstance constraintInstance;

                if(firstConstraintInstance.getName().equals(ParentConstraint.class.getSimpleName())) {
                    constraintInstance = firstConstraintInstance;
                }

                else {
                    constraintInstance = secondConstraintInstance;
                }

                for(ConstraintParameter parameter : constraintInstance.getParameters()) {
                    if(parameter.getName().equals("value")) {
                        testParameterValue(parameter.getValue(), Integer.toString(ConstraintArgumentValues.PARENT_CONSTRAINT_VALUE), constraintInstance.getName(), parameter.getName());
                    }
                }
            }

            else if(constraintDefinition.getName().equals(ParentConstraint.class.getSimpleName())) {
                assertEquals(constraintDefinition.getComposingConstraints().size(), 1, constraintDefinition.getName() + " must have one composing constraint");

                ConstraintInstance constraintInstance = (ConstraintInstance) constraintDefinition.getComposingConstraints().toArray()[0];
                assertEquals(constraintInstance.getName(), ChildConstraint.class.getSimpleName(), "Unexpected composing constraint found");
            }

            else if(constraintDefinition.getName().equals(ParentSiblingConstraint.class.getSimpleName())) {
                assertEquals(constraintDefinition.getComposingConstraints().size(), 1, constraintDefinition.getName() + " must have one composing constraint");

                ConstraintInstance constraintInstance = (ConstraintInstance) constraintDefinition.getComposingConstraints().toArray()[0];
                assertEquals(constraintInstance.getName(), NotNull.class.getSimpleName(), "Unexpected composing constraint found");
            }

            else if(constraintDefinition.getName().equals(ChildConstraint.class.getSimpleName())) {
                assertEquals(constraintDefinition.getComposingConstraints().size(), 2, constraintDefinition.getName() + " must have two composing constraints");

                Set<String> constraintNames = new HashSet<String>();
                ConstraintInstance firstConstraintInstance = (ConstraintInstance) constraintDefinition.getComposingConstraints().toArray()[0];
                ConstraintInstance secondConstraintInstance = (ConstraintInstance) constraintDefinition.getComposingConstraints().toArray()[1];

                constraintNames.add(firstConstraintInstance.getName());
                constraintNames.add(secondConstraintInstance.getName());

                assertTrue(constraintNames.contains(NotBlank.class.getSimpleName()), "NotBlank must be present");
                assertTrue(constraintNames.contains(Range.class.getSimpleName()), "Range must be present");
            }
        }
    }

    private String getFriendlyNameForProperty(String propertyName) {
        String[] parts = StringUtils.splitByCharacterTypeCamelCase(propertyName);

        for(int i = 0; i < parts.length ; i++) {
            parts[i] = parts[i].toLowerCase().replaceAll("\\.", " ");
        }

        parts[0] = StringUtils.capitalize(parts[0]);

        return StringUtils.join(parts, " ").replaceAll("\\s+", " ");
    }

    private void testParameterValue(String actual, String expected, String constraintName, String parameterName) {
        assertEquals(actual, expected, "Parameter values do not match [constraint: " + constraintName + ", parameter: " + parameterName + "]");
    }

    private void testClassLevelAndPropertyConstraints(Class clazz) {
        getValidationInformation(clazz);

        String className = StringUtils.uncapitalize(clazz.getSimpleName());

        BeanDescriptor classDescriptor = validator.getConstraintsForClass(clazz);
        int numClassLevelConstraints = classDescriptor.getConstraintDescriptors().size();
        int numConstrainedProperties = classDescriptor.getConstrainedProperties().size();

        //+ 1 for the class
        assertEquals(propertyToConstraintInstancesMap.keySet().size(), numConstrainedProperties + 1, "All properties in POJO are constrained, and it has (a) class-level constraint(s). But number of constrained properties do not match");
        assertNotNull(propertyToConstraintInstancesMap.get(className), "Map must contain an entry keyed by the class name");
        assertEquals(propertyToConstraintInstancesMap.get(className).size(), numClassLevelConstraints, "POJO doesn't have expected number of class-level constraints bound to it");
        assertEquals(compoundConstraintDefinitionSet.size(), 0, "POJO must not have any compound constraints");
    }
    
    private void testClassLevelConstraints(Class clazz, boolean hasCompoundConstraint) {
        getValidationInformation(clazz);

        String className = StringUtils.uncapitalize(clazz.getSimpleName());

        BeanDescriptor classDescriptor = validator.getConstraintsForClass(clazz);
        Set<ConstraintDescriptor<?>> classLevelConstraintDescriptors = classDescriptor.getConstraintDescriptors();

        assertEquals(propertyToConstraintInstancesMap.keySet().size(), 1, "There must only be one entry in the poperty to constraints map");
        assertNotNull(propertyToConstraintInstancesMap.get(className), "Map must contain an entry keyed by the class name");
        assertEquals(propertyToConstraintInstancesMap.get(className).size(), classLevelConstraintDescriptors.size(), "POJO doesn't have expected number of class-level constraints bound to it");

        if(!hasCompoundConstraint) {
            assertEquals(compoundConstraintDefinitionSet.size(), 0, "POJO must not have any compound constraints");
        }
    }

    private void getValidationInformation(Class clazz) {
        ClassConstraintInformation classConstraintInformation = validationConstraintsService.getValidationConstraints(clazz);
        propertyToConstraintInstancesMap = classConstraintInformation.getPropertyToConstraintInstancesMap();
        compoundConstraintDefinitionSet = classConstraintInformation.getCompoundConstraintDefinitionSet();
    }
}
