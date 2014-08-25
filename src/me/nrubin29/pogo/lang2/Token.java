package me.nrubin29.pogo.lang2;

public class Token {

    public enum TokenType {
        EMPTY, TOKEN, STRING_LITERAL
    }

    private TokenType type;
    private String token;

    public Token(TokenType type) {
        this(type, null);
    }

    public Token(TokenType type, String token) {
        this.type = type;
        this.token = token;
    }

    public TokenType getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return getClass() + " type=" + type + " token=" + token;
    }
}