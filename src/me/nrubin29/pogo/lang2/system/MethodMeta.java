package me.nrubin29.pogo.lang2.system;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.PrimitiveType;
import me.nrubin29.pogo.lang2.Type;
import me.nrubin29.pogo.lang2.Value;
import me.nrubin29.pogo.lang2.block.Class;
import me.nrubin29.pogo.lang2.block.Method;

/**
 * This is similar to the MethodParser class in lang except that this extends Class and contains subclasses which extend Method.
 * This method is better, especially using the Parser scheme.
 */
public class MethodMeta extends Class {

    public static MethodMeta TYPE = new MethodMeta(null);

    private Method method;

    public MethodMeta(Method method) {
        super("MethodMeta");

        this.method = method;

        add(new GetName());
        add(new GetReturnType());
    }

    @Override
    public boolean equalsType(Type other) {
        return other instanceof MethodMeta;
    }

    private class GetName extends SystemMethod {

        private GetName() {
            super(MethodMeta.this, "getName", PrimitiveType.STRING);
        }

        @Override
        public Value invoke() throws InvalidCodeException {
            return new Value(PrimitiveType.STRING, method.getName());
        }
    }

    private class GetReturnType extends SystemMethod {

        private GetReturnType() {
            super(MethodMeta.this, "getReturnType", PrimitiveType.STRING);
        }

        @Override
        public Value invoke() throws InvalidCodeException {
            return new Value(PrimitiveType.STRING, method.getType().toString());
        }
    }
}