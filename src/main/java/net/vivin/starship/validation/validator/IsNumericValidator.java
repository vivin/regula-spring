package net.vivin.starship.validation.validator;

import net.vivin.starship.validation.annotation.IsNumeric;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Scanner;

public class IsNumericValidator implements ConstraintValidator<IsNumeric, String> {
    @Override
    public void initialize(IsNumeric isNumeric) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean isNumeric = false;

        if(s != null) {
            Scanner scanner = new Scanner(s);
            isNumeric = scanner.hasNextDouble();
        }

        return isNumeric;
    }
}
