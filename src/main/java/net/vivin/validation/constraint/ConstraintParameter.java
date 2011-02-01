package net.vivin.validation.constraint;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ConstraintParameter {

    private String name;
    private String value;
    private String type;

    public ConstraintParameter(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
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

        ConstraintParameter rhs = (ConstraintParameter) o;

        return new EqualsBuilder()
                .append(name, rhs.name)
                .append(value, rhs.value)
                .append(type, rhs.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 7)
                .append(name)
                .append(value)
                .append(type)
                .toHashCode();
    }
}
