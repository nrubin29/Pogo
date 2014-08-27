package me.nrubin29.pogo.lang2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    public static Value parseToken(Token token, Block block) throws InvalidCodeException {
        if (token.getType() == Token.TokenType.BOOLEAN_LITERAL) {
            return new Value(PrimitiveType.BOOLEAN, token.getToken());
        }

        else if (token.getType() == Token.TokenType.DOUBLE_LITERAL) {
            return new Value(PrimitiveType.DOUBLE, token.getToken());
        }

        else if (token.getType() == Token.TokenType.INTEGER_LITERAL) {
            return new Value(PrimitiveType.INTEGER, token.getToken());
        }

        else if (token.getType() == Token.TokenType.STRING_LITERAL) {
            return new Value(PrimitiveType.STRING, token.getToken());
        }

        else {
            if (block != null) {
                if (block.hasVariable(token.getToken())) {
                    return block.getVariable(token.getToken()).get();
                }

                else {
                    throw new InvalidCodeException("Expected variable, found " + token.getToken());
                }
            }
        }

        return new Value(null);
    }

    public static String[] readFile(File file, boolean keepEmptyLines) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            Stream<String> lines = reader.lines();

            if (!keepEmptyLines) {
                lines = lines.filter(line -> !line.equals(""));
            }

            List<String> contents = lines.collect(Collectors.toList());

            return contents.toArray(new String[contents.size()]);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String readFile(File file, boolean keepEmptyLines, String glue) {
        StringBuilder builder = new StringBuilder();

        Arrays.stream(readFile(file, keepEmptyLines)).forEach(str -> builder.append(str).append(glue));

        return builder.toString();
    }
}