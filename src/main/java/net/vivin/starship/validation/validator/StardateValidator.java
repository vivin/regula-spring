package net.vivin.starship.validation.validator;

import net.vivin.starship.validation.annotation.Stardate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StardateValidator implements ConstraintValidator<Stardate, String> {
    @Override
    public void initialize(Stardate stardate) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern stardatePattern = Pattern.compile("^[0-9]+\\.[0-9]$", Pattern.DOTALL);
        Matcher matchesStardate = stardatePattern.matcher(s);
        return matchesStardate.matches();
    }
}
