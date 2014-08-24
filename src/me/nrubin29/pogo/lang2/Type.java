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
            type = IDEInstance.CURRENT_INSTANCE.getPogoClass(str);
        }

        if (type == null) {
            throw new InvalidCodeException("Expected type, got " + str);
        }

        return type;
    }
}