package net.vivin.validation.constraint;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

public class ConstraintInstance {

    private String name;
    private Set<ConstraintParameter> parameters;
    private boolean classLevelConstraint;

    public ConstraintInstance() {
        parameters = new HashSet<ConstraintParameter>();
        classLevelConstraint = false;
    }

    public ConstraintInstance(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Set<ConstraintParameter> getParameters() {
        return new HashSet<ConstraintParameter>(parameters);
    }

    public void addParameter(ConstraintParameter parameter) {
        parameters.add(parameter);
    }

    public boolean isClassLevelConstraint() {
        return classLevelConstraint;
    }

    public void setClassLevelConstraint(boolean classLevelConstraint) {
        this.classLevelConstraint = classLevelConstraint;
    }

    @Override
    public boolean equals(Object o) {

        if(o == null) {
            return false;
        }

        if(o == this) {
            return true;
        }

        if(o.getClass() != getClass()) {
            return false;
        }

        ConstraintInstance rhs = (ConstraintInstance) o;

        return new EqualsBuilder()
                .append(name, rhs.name)
                .append(parameters, rhs.parameters)
                .append(classLevelConstraint, rhs.classLevelConstraint)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 13)
                .append(name)
                .append(parameters)
                .append(classLevelConstraint)
                .toHashCode();
    }

}
