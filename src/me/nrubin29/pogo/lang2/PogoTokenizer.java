package me.nrubin29.pogo.lang2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PogoTokenizer {

    private ArrayList<Pattern> patterns;
    private String str;
    private Token lastToken;
    private boolean pushBack;

    public PogoTokenizer(String str) {
        this.patterns = new ArrayList<>();
        this.str = str;

        for (Comparison comp : Comparison.values()) {
            patterns.add(Pattern.compile("^(" + comp.getToken() + ")"));
        }

        String[] tokens = { "\"", "=", "\\(", "\\)", "\\.", "\\,", "//" };

        for (String token : tokens) {
            patterns.add(Pattern.compile("^(" + token + ")"));
        }

        patterns.add(Pattern.compile("^([a-zA-Z][^\n| |" + String.join("|", tokens) + "]*)"));
    }

    public Token nextToken() throws InvalidCodeException {
        str = str.trim();

        if (pushBack) {
            pushBack = false;
            return lastToken;
        }

        if (str.equals("")) {
            return lastToken = new Token(Token.TokenType.EMPTY);
        }

        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(str);

            if (matcher.find()) {
                String token = matcher.group().trim();

                if (token.equals("\"")) {
                    StringBuilder builder = new StringBuilder();

                    for (int i = 1; i < str.length(); i++) {
                        if (str.charAt(i) == '"') {
                            break;
                        }

                        else {
                            builder.append(str.charAt(i));
                        }
                    }

                    str = str.substring(builder.length() + 2);

                    return lastToken = new Token(Token.TokenType.STRING_LITERAL, builder.toString());
                }

                else if (token.startsWith("//")) {
                    str = str.substring(str.indexOf('\n') != -1 ? str.indexOf('\n') : str.length());

                    return lastToken = new Token(Token.TokenType.EMPTY);
                }

                str = matcher.replaceFirst("");

                return lastToken = new Token(Token.TokenType.TOKEN, token);
            }
        }

        throw new InvalidCodeException("Unknown token: " + str.substring(0, str.indexOf(" ")));
    }

    public boolean hasNextToken() {
        return !str.equals("");
    }

    public void pushBack() {
        if (lastToken != null) {
            this.pushBack = true;
        }
    }
}