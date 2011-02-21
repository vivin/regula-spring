package net.vivin.regula.validation.util;

import java.util.regex.Pattern;

public class TagStringUtils {
    //Checks to see if a string matches a regular expression
    public static boolean matches(String pattern, String string, String flags) {
        if(flags != null && !flags.trim().equals("")) {
            pattern = "(?" + flags + ")" + pattern;
        }

        return Pattern.compile(pattern).matcher(string).matches();
    }

    //Search and replace with regex
    public static String replaceUsingRegex(String pattern, String flags, String source, String replacement) {
        if(flags != null && !flags.trim().equals("")) {
            pattern = "(?" + flags + ")" + pattern;
        }

        return Pattern.compile(pattern, Pattern.DOTALL).matcher(source).replaceFirst(replacement);
    }
}
