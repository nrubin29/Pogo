package me.nrubin29.pogo;

import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

import java.util.Arrays;

public class Utils {

    public static String implode(String s, Block block) throws InvalidCodeException {
        StringBuilder builder = new StringBuilder();

        boolean inQuotes = false;

        for (String str : s.split(" ")) {
            if (str.startsWith("\"")) inQuotes = true;

            if (inQuotes) builder.append(str.replaceAll("\"", ""));

            else {
                if (block != null) {
                    try {
                        Variable v = block.getVariable(str.contains(":") ? str.substring(0, str.lastIndexOf(":")) : str);
                        if (v.isArray()) {
                            if (str.contains(":"))
                                builder.append(v.getValue(Integer.parseInt(implode(str.substring(str.lastIndexOf(":") + 1), block))));
                            else builder.append(Arrays.toString(v.getValues()));
                        } else builder.append(v.getValue());
                    } catch (InvalidCodeException e) {
                        if (e.getMessage().equals("Index does not exist.")) throw e;
                        builder.append(str);
                    }
                }
            }

            builder.append(" ");

            if (str.endsWith("\"")) inQuotes = false;
        }

        return builder.toString().trim();
    }

    public static String[] implode(String[] s, Block block) throws InvalidCodeException {
        String[] imploded = new String[s.length];

        for (int i = 0; i < s.length; i++) {
            imploded[i] = implode(s[i], block);
        }

        return imploded;
    }

    public static String changeCommas(String commaStr) {
        StringBuilder builder = new StringBuilder();

        boolean inQuotes = false;

        for (String str : commaStr.split(" ")) {
            for (char c : str.toCharArray()) {
                if (c == ' ') {
                    builder.append(" ");
                } else if (c == '"') {
                    inQuotes = !inQuotes;
                    builder.append("\"");
                } else if (c == ',') {
                    if (inQuotes) builder.append("__comma__");
                    else builder.append(",");
                } else {
                    builder.append(c);
                }
            }
            builder.append(" ");
        }

        return builder.toString().trim();
    }

    public static String unchangeCommas(String commaStr) {
        return commaStr.replaceAll("__comma__", ",").trim();
    }
}
