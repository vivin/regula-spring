package net.vivin.regula.validation.constraint;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import java.util.HashSet;
import java.util.Set;

public class ConstraintDefinition {
    private String name;
    private Set<String> parameters;
    private Set<ConstraintInstance> composingConstraints;
    private boolean reportAsSingleViolation;

    public ConstraintDefinition() {
        parameters = new HashSet<String>();
        composingConstraints = new HashSet<ConstraintInstance>();
        reportAsSingleViolation = false;
    }

    public ConstraintDefinition(ConstraintInstance constraintInstance) {
        this();

        name = constraintInstance.getName();

        for(ConstraintParameter parameter : constraintInstance.getParameters()) {
            parameters.add(parameter.getName());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getParameters() {
        return new HashSet<String>(parameters);
    }

    public Set<ConstraintInstance> getComposingConstraints() {
        return new HashSet<ConstraintInstance>(composingConstraints);
    }

    public boolean isReportAsSingleViolation() {
        return reportAsSingleViolation;
    }

    public void setReportAsSingleViolation(boolean reportAsSingleViolation) {
        this.reportAsSingleViolation = reportAsSingleViolation;
    }

    public void addParameter(String parameter) {
        parameters.add(parameter);
    }

    public void addComposingConstraint(ConstraintInstance composingConstraintInstance) {
        composingConstraints.add(composingConstraintInstance);
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

        ConstraintDefinition rhs = (ConstraintDefinition) o;

        return new EqualsBuilder()
                .append(name, rhs.name)
                .append(parameters, rhs.parameters)
                .append(composingConstraints, rhs.composingConstraints)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 19)
                .append(name)
                .append(parameters)
                .append(composingConstraints)
                .toHashCode();
    }

}
