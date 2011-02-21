package net.vivin.regula.validation.service.testcaseconstraint;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface AnotherClassLevelConstraint {
    String message() default "{net.vivin.regula.validation.service.testcaseconstraint.AnotherClassLevelConstraint}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
