package net.vivin.regula.validation.service.testcaseconstraint;

import net.vivin.regula.validation.service.ConstraintArgumentValues;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@ReportAsSingleViolation
@GreatGrandParentConstraint(ConstraintArgumentValues.GREAT_GRANDPARENT_CONSTRAINT_VALUE)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface GreatGreatGrandParentConstraint {
    int aParameter() default 0;
    String message() default "{net.vivin.regula.validation.service.testcaseconstraint.GreatGreatGrandParentConstraint}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
