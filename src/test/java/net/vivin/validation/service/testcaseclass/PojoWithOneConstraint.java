package net.vivin.validation.service.testcaseclass;

import org.hibernate.validator.constraints.NotBlank;

public class PojoWithOneConstraint {
    @NotBlank
    private int intField;
    
    private float floatField;
    private double doubleField;
    private String stringField;
    private boolean booleanField;
}
