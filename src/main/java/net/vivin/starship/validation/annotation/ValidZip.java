package net.vivin.starship.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@IsNumeric
@Pattern(regexp = "^[0-9]{5}$", flags = {Pattern.Flag.DOTALL})
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidZip {
    String message() default "{net.vivin.starship.validation.annotation.ValidZip}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}