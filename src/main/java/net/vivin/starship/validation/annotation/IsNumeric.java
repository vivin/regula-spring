package net.vivin.starship.validation.annotation;

import net.vivin.starship.validation.validator.IsNumericValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsNumericValidator.class)
@Documented
public @interface IsNumeric {
    String message() default "{net.vivin.starship.validation.annotation.IsNumeric}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
