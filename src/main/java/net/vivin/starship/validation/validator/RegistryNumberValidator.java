package net.vivin.starship.validation.validator;

import net.vivin.starship.validation.annotation.RegistryNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistryNumberValidator implements ConstraintValidator<RegistryNumber, String> {
    @Override
    public void initialize(RegistryNumber registryNumber) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern registryNumberPattern = Pattern.compile("^N(CC|X)-[0-9]+(-[A-Z])?$", Pattern.DOTALL);
        Matcher matchesRegistryNumber = registryNumberPattern.matcher(s);
        return matchesRegistryNumber.matches();
    }
}
