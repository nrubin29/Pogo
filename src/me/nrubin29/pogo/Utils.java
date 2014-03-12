package me.nrubin29.pogo;

import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

public class Utils {

    public static String implode(String s, Block block) {
        StringBuilder builder = new StringBuilder();

        boolean inQuotes = false;

        for (String str : s.split(" ")) {
            if (str.startsWith("\"")) inQuotes = true;

            if (inQuotes) builder.append(str.replaceAll("\"", ""));

            else {
                if (block != null) {
                    boolean isPossiblyArray = str.contains("[") && str.contains("]") && str.substring(str.lastIndexOf("[") + 1, str.lastIndexOf("]")).matches("[/[0-9]+/]");
                    try {
                        Variable v = block.getVariable(isPossiblyArray ? str.substring(0, str.lastIndexOf("[")) : str);
                        if (v.isArray())
                            builder.append(v.getValues()[Integer.parseInt(str.substring(str.lastIndexOf("[") + 1, str.lastIndexOf("]")))]);
                        else builder.append(v.getValue());
                    } catch (InvalidCodeException e) {
                        builder.append(str);
                    }
                }
            }

            builder.append(" ");

            if (str.endsWith("\"")) inQuotes = false;
        }

        return builder.toString().trim();
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
                } else if (c == ',') {
                    if (inQuotes) builder.append("__comma__");
                    else builder.append(",");
                } else {
                    builder.append(c);
                }
            }
        }

        return builder.toString().trim();
    }

    public static String unchangeCommas(String commaStr) {
        return commaStr.replaceAll("__comma__", ",").trim();
    }
}
