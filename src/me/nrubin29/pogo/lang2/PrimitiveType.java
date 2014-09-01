package me.nrubin29.pogo.lang2;

public enum PrimitiveType implements Type {

    BOOLEAN, DOUBLE, INTEGER, OBJECT, STRING, VOID;

    @Override
    public boolean equalsType(Type other) {
        return other instanceof PrimitiveType && (other == this || other == OBJECT || this == OBJECT);
    }
}