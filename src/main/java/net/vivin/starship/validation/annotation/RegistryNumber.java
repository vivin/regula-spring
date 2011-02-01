package net.vivin.starship.validation.annotation;

import net.vivin.starship.validation.validator.RegistryNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegistryNumberValidator.class)
@Documented
public @interface RegistryNumber {
    String message() default "{net.vivin.starship.validation.annotation.RegistryNumber}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
