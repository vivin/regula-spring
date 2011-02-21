package net.vivin.regula.validation.service.testcaseconstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@ChildConstraint
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ParentConstraint {
    int value() default 0;
    String message() default "{net.vivin.regula.validation.service.testcaseconstraint.ParentConstraint}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
