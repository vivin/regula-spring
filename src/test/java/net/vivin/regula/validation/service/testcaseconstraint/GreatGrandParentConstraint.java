package net.vivin.regula.validation.service.testcaseconstraint;

import net.vivin.regula.validation.service.ConstraintArgumentValues;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@GrandParentConstraint(ConstraintArgumentValues.GRANDPARENT_CONSTRAINT_VALUE)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface GreatGrandParentConstraint {
    int value() default 0;
    String message() default "{net.vivin.regula.validation.service.testcaseconstraint.GreatGrandParentConstraint}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
