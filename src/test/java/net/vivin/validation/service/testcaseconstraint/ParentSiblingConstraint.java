package net.vivin.validation.service.testcaseconstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@NotNull
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ParentSiblingConstraint {
    String message() default "{net.vivin.validation.service.testcaseconstraint.ParentConstraint}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
