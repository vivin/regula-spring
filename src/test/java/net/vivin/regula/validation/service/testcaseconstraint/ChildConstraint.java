package net.vivin.regula.validation.service.testcaseconstraint;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Range(min=1, max=10)
@NotBlank
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ChildConstraint {
    String message() default "{net.vivin.regula.validation.service.testcaseconstraint.ChildConstraint}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
