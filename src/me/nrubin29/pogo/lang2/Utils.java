package me.nrubin29.pogo.lang2;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    public static Value handleVariables(String sval, int ttype, Block block) throws InvalidCodeException, IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(sval));
        tokenizer.nextToken();
        tokenizer.ttype = ttype;
        return handleVariables(tokenizer, block);
    }

    public static Value handleVariables(StreamTokenizer tokenizer, Block block) throws InvalidCodeException, IOException {
        StringBuilder builder = new StringBuilder();

        tokenizer.pushBack();

        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF && tokenizer.ttype != StreamTokenizer.TT_EOL) {
            if (tokenizer.ttype == '"') {
                builder.append(tokenizer.sval);
            }

            else {
                String str = tokenizer.sval;

                if (block != null) {
                    if (block.hasVariable(str)) {
                        builder.append(block.getVariable(str).get().getValue());
                    }

                    else {
                        throw new InvalidCodeException("Expected variable, found " + str);
                    }
                }

                else {
                    throw new InvalidCodeException("Expected variable, found " + str); // Should probably reword this.
                }

                builder.append(" ");
            }
        }

        return new Value(PrimitiveType.STRING, builder.toString().trim()); // Will this always be a string? Maybe not with boolean
    }

    public static StreamTokenizer tokenize(String str) {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(str));
        tokenizer.slashSlashComments(true);
        tokenizer.slashStarComments(true);
        tokenizer.ordinaryChar('!');
        tokenizer.ordinaryChar('=');
        tokenizer.ordinaryChar('>');
        tokenizer.ordinaryChar('<');
        tokenizer.ordinaryChar('(');
        tokenizer.ordinaryChar(')');
        return tokenizer;
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