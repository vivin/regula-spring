package net.vivin.validation.service.testcaseclass;

import net.vivin.validation.service.testcaseconstraint.GreatGreatGrandParentConstraint;

public class PojoWithCompoundConstraint {

    @GreatGreatGrandParentConstraint
    private String stringField;

    @GreatGreatGrandParentConstraint
    private int intField;
}
