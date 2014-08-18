package me.nrubin29.pogo.lang2;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static String handleVariables(String s, Block block) throws InvalidCodeException, IOException {
        StreamTokenizer tokenizer = tokenize(s);
        StringBuilder builder = new StringBuilder();
        boolean inQuotes = false;

        int code;
        while ((code = tokenizer.nextToken()) == StreamTokenizer.TT_WORD || code == StreamTokenizer.TT_NUMBER) {
            String str = tokenizer.sval;

            if (str.startsWith("\"")) {
                inQuotes = true;
            }

            if (inQuotes) {
                builder.append(str.replaceAll("\"", ""));
            } else {
                if (block != null) {
                    if (block.hasVariable(str)) {
                        builder.append(block.getVariable(str).get().getValue());
                    } else {
                        throw new InvalidCodeException("Expected variable, found " + str);
                    }
                }
            }

            builder.append(" ");

            if (str.endsWith("\"")) {
                inQuotes = false;
            }
        }

        return builder.toString().trim();
    }

    public static StreamTokenizer tokenize(String str) {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(str)); // I think this should be ok?
        tokenizer.slashSlashComments(true);
        tokenizer.slashStarComments(true);
        tokenizer.wordChars('=', '=');
        tokenizer.wordChars('"', '"');
        tokenizer.wordChars('(', '(');
        tokenizer.wordChars(')', ')');
        tokenizer.wordChars(',', ',');
        return tokenizer;
    }

    public static String[] readFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            List<String> contents = reader.lines().collect(Collectors.toList());

            return contents.toArray(new String[contents.size()]);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String readFile(File file, String glue) {
        StringBuilder builder = new StringBuilder();

        Arrays.stream(readFile(file)).forEach(str -> builder.append(str).append(glue));

        return builder.toString();
    }
}