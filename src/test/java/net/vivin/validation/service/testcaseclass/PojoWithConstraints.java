package net.vivin.validation.service.testcaseclass;

import net.vivin.validation.service.ConstraintArgumentValues;
import net.vivin.validation.service.testcaseconstraint.FirstGroup;
import net.vivin.validation.service.testcaseconstraint.GreatGreatGrandParentConstraint;
import net.vivin.validation.service.testcaseconstraint.SecondGroup;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class PojoWithConstraints {

    @Min(value=ConstraintArgumentValues.MIN_VALUE, message=ConstraintArgumentValues.MESSAGE)
    private int intField;

    @Max(ConstraintArgumentValues.MAX_VALUE)
    private float floatField;

    @Range(min=ConstraintArgumentValues.MIN_VALUE, max=ConstraintArgumentValues.MAX_VALUE)
    private double doubleField;

    @Pattern(regexp=ConstraintArgumentValues.REGEXP, flags={Pattern.Flag.DOTALL, Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.MULTILINE})
    private String stringField;

    @NotBlank(groups={FirstGroup.class, SecondGroup.class})
    private boolean booleanField;
}
