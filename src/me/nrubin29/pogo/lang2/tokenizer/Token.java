package me.nrubin29.pogo.lang2.tokenizer;

import me.nrubin29.pogo.lang2.PrimitiveType;

public class Token {

    public enum TokenType {
        EMPTY(PrimitiveType.VOID),
        TOKEN(null),
        IDENTIFIER(null),
        BOOLEAN_LITERAL(PrimitiveType.BOOLEAN),
        DOUBLE_LITERAL(PrimitiveType.DOUBLE),
        INTEGER_LITERAL(PrimitiveType.INTEGER),
        STRING_LITERAL(PrimitiveType.STRING),
        STRING_PART(PrimitiveType.STRING),
        PROPERTY(null);

        public static final TokenType[] NOT_TOKEN = { BOOLEAN_LITERAL, DOUBLE_LITERAL, INTEGER_LITERAL, STRING_LITERAL, IDENTIFIER, PROPERTY };

        private PrimitiveType primitiveType;

        private TokenType(PrimitiveType primitiveType) {
            this.primitiveType = primitiveType;
        }

        public PrimitiveType getPrimitiveType() {
            return primitiveType;
        }
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