package net.vivin.regula.validation.service.testcaseclass;

import net.vivin.regula.validation.service.testcaseconstraint.GreatGreatGrandParentConstraint;

public class PojoWithCompoundConstraint {

    @GreatGreatGrandParentConstraint
    private String stringField;

    @GreatGreatGrandParentConstraint
    private int intField;
}
