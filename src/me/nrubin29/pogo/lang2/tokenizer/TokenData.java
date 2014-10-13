package me.nrubin29.pogo.lang2.tokenizer;

import java.util.regex.Pattern;

public class TokenData {

    private Pattern pattern;
    private Token.TokenType tokenType;

    public TokenData(Pattern pattern, Token.TokenType tokenType) {
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
