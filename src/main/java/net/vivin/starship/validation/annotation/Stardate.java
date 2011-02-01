package net.vivin.starship.validation.annotation;

import net.vivin.starship.validation.validator.StardateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StardateValidator.class)
@Documented
public @interface Stardate {
    String message() default "{net.vivin.starship.validation.annotation.Stardate}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
