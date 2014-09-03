package me.nrubin29.pogo.lang2;

import java.util.Arrays;
import java.util.StringJoiner;

public class Regex {

    public static final String IDENTIFIER = "[a-zA-Z]([a-zA-Z0-9]*)?";
    public static final String STRING_LITERAL = "\"(.*)?\"";
    public static final String DOUBLE_LITERAL = "(-)?([.])?[0-9][0-9.]*";
    public static final String INTEGER_LITERAL = "(-)?[0-9]*";
    public static final String BOOLEAN_LITERAL = "(true|false)";
    public static final String PROPERTY = "@" + IDENTIFIER;
    public static final String COMPARISON = "(" + join(Comparison.values(), "|") + ")";
    public static final String IDENTIFIER_OR_LITERAL = "(" + IDENTIFIER + "|" + DOUBLE_LITERAL + "|" + BOOLEAN_LITERAL + "|" + STRING_LITERAL + "|" + INTEGER_LITERAL + ")";

    private static <T> String join(T[] elements, String delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter);

        Arrays.stream(elements).forEach(element -> joiner.add(element.toString().toLowerCase()));

        return joiner.toString();
    }
}