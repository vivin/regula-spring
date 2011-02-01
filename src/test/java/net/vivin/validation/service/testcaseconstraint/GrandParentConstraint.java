package net.vivin.validation.service.testcaseconstraint;

import net.vivin.validation.service.ConstraintArgumentValues;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@ParentConstraint(ConstraintArgumentValues.PARENT_CONSTRAINT_VALUE)
@ParentSiblingConstraint
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface GrandParentConstraint {
    int value() default 0;
    String message() default "{net.vivin.validation.service.testcaseconstraint.GrandParentConstraint}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
