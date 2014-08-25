package me.nrubin29.pogo.lang2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PogoTokenizer {

    private ArrayList<TokenData> patterns;
    private String str;
    private Token lastToken;
    private boolean pushBack;

    public PogoTokenizer(String str) {
        this.patterns = new ArrayList<>();
        this.str = str;

        for (Comparison comp : Comparison.values()) {
            patterns.add(new TokenData(Pattern.compile("^(" + comp.getToken() + ")"), Token.TokenType.TOKEN));
        }

        String[] tokens = { "=", "\\(", "\\)", "\\.", "\\,", };

        for (String token : tokens) {
            patterns.add(new TokenData(Pattern.compile("^(" + token + ")"), Token.TokenType.TOKEN));
        }

        patterns.add(new TokenData(Pattern.compile("^(//)"), Token.TokenType.EMPTY));
        patterns.add(new TokenData(Pattern.compile("^(\")"), Token.TokenType.STRING_LITERAL));
        patterns.add(new TokenData(Pattern.compile("^(true|false)"), Token.TokenType.BOOLEAN_LITERAL));
        patterns.add(new TokenData(Pattern.compile("^([a-zA-Z][^\n| |" + String.join("|", tokens) + "]*)"), Token.TokenType.TOKEN));
        patterns.add(new TokenData(Pattern.compile("^((-)?[0-9][0-9]*)"), Token.TokenType.INTEGER_LITERAL));
        patterns.add(new TokenData(Pattern.compile("^((-)?[0-9][0-9.]*)"), Token.TokenType.DOUBLE_LITERAL));
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

        for (TokenData data : patterns) {
            Matcher matcher = data.getPattern().matcher(str);

            if (matcher.find()) {
                String token = matcher.group().trim();

                if (data.getTokenType() == Token.TokenType.BOOLEAN_LITERAL) {
                    str = matcher.replaceFirst("");
                    return lastToken = new Token(Token.TokenType.BOOLEAN_LITERAL, token);
                }

                else if (data.getTokenType() == Token.TokenType.INTEGER_LITERAL) {
                    str = matcher.replaceFirst("");
                    return lastToken = new Token(Token.TokenType.INTEGER_LITERAL, token);
                }

                else if (data.getTokenType() == Token.TokenType.DOUBLE_LITERAL) {
                    str = matcher.replaceFirst("");
                    return lastToken = new Token(Token.TokenType.DOUBLE_LITERAL, token);
                }

                else if (data.getTokenType() == Token.TokenType.EMPTY) {
                    str = str.substring(str.indexOf('\n') != -1 ? str.indexOf('\n') : str.length());

                    return lastToken = new Token(Token.TokenType.EMPTY);
                }

                else if (data.getTokenType() == Token.TokenType.STRING_LITERAL) {
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

                else {
                    str = matcher.replaceFirst("");
                    return lastToken = new Token(Token.TokenType.TOKEN, token);
                }
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

    private class TokenData {

        private Pattern pattern;
        private Token.TokenType tokenType;

        private TokenData(Pattern pattern, Token.TokenType tokenType) {
            this.pattern = pattern;
            this.tokenType = tokenType;
        }

        public Pattern getPattern() {
            return pattern;
        }

        public Token.TokenType getTokenType() {
            return tokenType;
        }
    }
}