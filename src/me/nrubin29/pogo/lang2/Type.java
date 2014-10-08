package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.lang2.system.MethodMeta;
import me.nrubin29.pogo.lang2.system.SystemClass;
import me.nrubin29.pogo.lang2.system.VariableMeta;

/**
 * Represents a value that can be returned from a method. Subclasses are {@link me.nrubin29.pogo.lang2.block.Class} and {@link me.nrubin29.pogo.lang2.Variable}.
 */
public interface Type {

    public static Type match(String str) throws InvalidCodeException {
        Type type = null;

        if (str.equals("System")) {
            type = SystemClass.getInstance();
        }

        else if (str.equals("MethodMeta")) {
            type = MethodMeta.TYPE;
        }

        else if (str.equals("VariableMeta")) {
            type = VariableMeta.TYPE;
        }

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

    public boolean equalsType(Type other);
}