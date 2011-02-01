package net.vivin.validation.service;

import net.vivin.validation.constraint.ConstraintDefinition;
import net.vivin.validation.constraint.ConstraintInstance;

import java.util.Map;
import java.util.Set;

public class ClassConstraintInformation {
    private Map<String, Set<ConstraintInstance>> propertyToConstraintInstancesMap;
    private Set<ConstraintDefinition> compoundConstraintDefinitionSet;

    //package private so that only ValidationConstraintsService can create a new instance
    ClassConstraintInformation(Map<String, Set<ConstraintInstance>> propertyToConstraintInstancesMap, Set<ConstraintDefinition> compoundConstraintDefinitionSet) {
        this.propertyToConstraintInstancesMap = propertyToConstraintInstancesMap;
        this.compoundConstraintDefinitionSet = compoundConstraintDefinitionSet;
    }

    public Map<String, Set<ConstraintInstance>> getPropertyToConstraintInstancesMap() {
        return propertyToConstraintInstancesMap;
    }

    public Set<ConstraintDefinition> getCompoundConstraintDefinitionSet() {
        return compoundConstraintDefinitionSet;
    }
}
