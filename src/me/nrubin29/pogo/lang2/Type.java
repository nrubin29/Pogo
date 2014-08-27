package me.nrubin29.pogo.lang2;

/**
 * Represents a value that can be returned from a method. Subclasses are {@link me.nrubin29.pogo.lang2.Class} and {@link me.nrubin29.pogo.lang2.Variable}.
 */
public interface Type {

    public static Type match(String str) throws InvalidCodeException {
        Type type = null;

        try {
            type = PrimitiveType.valueOf(str.toUpperCase());
        } catch (Exception ignored) {

        }

        if (type == null) {
            type = Runtime.RUNTIME.getPogoClass(str);
        }

        if (type == null) {
            throw new InvalidCodeException("Expected type, got " + str);
        }

        return type;
    }

//    public boolean isTokenType(String type);

//    public default boolean isTokenType(Token.TokenType type) {
//        return isTokenType(type.getPrimitiveType().name());
//    }
}