package me.nrubin29.pogo.lang2;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static String handleVariables(StreamTokenizer tokenizer, Block block) throws InvalidCodeException, IOException {
        StringBuilder builder = new StringBuilder();

        tokenizer.pushBack();

        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF && tokenizer.ttype != StreamTokenizer.TT_EOL) {
            if (tokenizer.ttype == '"') {
                builder.append(tokenizer.sval);
            } else {
                String str = tokenizer.sval;

                if (block != null) {
                    if (block.hasVariable(str)) {
                        builder.append(block.getVariable(str).get().getValue());
                    } else {
                        throw new InvalidCodeException("Expected variable, found " + str);
                    }
                }

                builder.append(" ");
            }
        }

        return builder.toString().trim();
    }

    public static StreamTokenizer tokenize(String str) {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(str));
        tokenizer.slashSlashComments(true);
        tokenizer.slashStarComments(true);
        tokenizer.ordinaryChar('=');
        tokenizer.ordinaryChar('(');
        tokenizer.ordinaryChar(')');
        return tokenizer;
    }

    public static String[] readFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            List<String> contents = reader.lines().filter(line -> !line.equals("")).collect(Collectors.toList());

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