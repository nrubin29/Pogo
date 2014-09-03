package me.nrubin29.pogo.lang2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PogoTokenizer implements Cloneable {

    private ArrayList<TokenData> patterns;
    private String str;
    private Token lastToken;
    private boolean pushBack;

    public PogoTokenizer(String str) {
        this.patterns = new ArrayList<>();
        this.str = str;

        patterns.add(new TokenData(Pattern.compile("^(" + Regex.COMPARISON + ")"), Token.TokenType.TOKEN));

        for (String token : new String[] { "=", "\\(", "\\)", "\\.", "\\,", "->" }) {
            patterns.add(new TokenData(Pattern.compile("^(" + token + ")"), Token.TokenType.TOKEN));
        }

        patterns.add(new TokenData(Pattern.compile("^(" + Regex.IDENTIFIER + ")"), Token.TokenType.IDENTIFIER));
        patterns.add(new TokenData(Pattern.compile("^(//.*)"), Token.TokenType.EMPTY));
        patterns.add(new TokenData(Pattern.compile("^(" + Regex.STRING_LITERAL + ")"), Token.TokenType.STRING_LITERAL));
        patterns.add(new TokenData(Pattern.compile("^(" + Regex.BOOLEAN_LITERAL + ")"), Token.TokenType.BOOLEAN_LITERAL));
        patterns.add(new TokenData(Pattern.compile("^(" + Regex.INTEGER_LITERAL + ")"), Token.TokenType.INTEGER_LITERAL));
        patterns.add(new TokenData(Pattern.compile("^(" + Regex.DOUBLE_LITERAL + ")"), Token.TokenType.DOUBLE_LITERAL));
        patterns.add(new TokenData(Pattern.compile("^(" + Regex.PROPERTY + ")"), Token.TokenType.PROPERTY));
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

                if (data.getTokenType() == Token.TokenType.IDENTIFIER) {
                    str = matcher.replaceAll("");
                    return lastToken = new Token(Token.TokenType.IDENTIFIER, token);
                }

                else if (data.getTokenType() == Token.TokenType.BOOLEAN_LITERAL) {
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
                    str = "";
                    return lastToken = new Token(Token.TokenType.EMPTY);
                }

                else if (data.getTokenType() == Token.TokenType.STRING_LITERAL) {
                    str = matcher.replaceFirst("");
                    return lastToken = new Token(Token.TokenType.STRING_LITERAL, token.substring(1, token.length() - 1));
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

    public int getNumberOfTokens(Token.TokenType... types) throws InvalidCodeException {
        String localStr = str;
        int count = 0;

        while (hasNextToken()) {
            Token token = nextToken();

            for (Token.TokenType type : types) {
                if (token.getType().equals(type)) {
                    count++;
                    break;
                }
            }
        }

        this.str = localStr;
        return count;
    }

    @Override
    public PogoTokenizer clone() {
        return new PogoTokenizer(str);
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

        @Override
        public String toString() {
            return getClass() + " pattern=" + pattern + " tokenType=" + tokenType;
        }
    }
}