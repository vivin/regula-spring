package net.vivin.regula.validation.service.testcaseclass;

import net.vivin.regula.validation.service.ConstraintArgumentValues;
import net.vivin.regula.validation.service.testcaseconstraint.ClassLevelConstraint;

import javax.validation.constraints.Min;

@ClassLevelConstraint
public class PojoWithOneClassLevelConstraintAndOnePropertyConstraint {
    @Min(ConstraintArgumentValues.MIN_VALUE)
    private int intField;
    private float floatField;
    private double doubleField;
    private String stringField;
    private boolean booleanField;
}
