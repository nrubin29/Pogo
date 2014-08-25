package me.nrubin29.pogo.lang2;

public class Value {

    private Type type;
    private Object value;

    public Value(Object value) {
        this(null, value);
    }

    public Value(Type type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        if (type == null) {
            return value;
        }

        if (type == PrimitiveType.BOOLEAN) {
            return Boolean.valueOf(value.toString());
        }

        else if (type == PrimitiveType.DOUBLE) {
            return Double.valueOf(value.toString());
        }

        else if (type == PrimitiveType.INTEGER) {
            return Integer.valueOf(value.toString());
        }

        else if (type == PrimitiveType.STRING) {
            return value.toString();
        }

        else {
            return value;
        }
    }

    public void setValue(Object value) {
        if (type == null) {
            this.value = value;
            return;
        }

        if (type == PrimitiveType.BOOLEAN) {
            this.value = Boolean.valueOf(value.toString());
        }

        else if (type == PrimitiveType.DOUBLE) {
            this.value = Double.valueOf(value.toString());
        }

        else if (type == PrimitiveType.INTEGER) {
            this.value = Integer.valueOf(value.toString());
        }

        else if (type == PrimitiveType.STRING) {
            this.value = value.toString();
        }

        else {
            this.value = value;
        }
    }

    @Override
    public String toString() {
        return getClass() + " type=" + type + " value=" + value;
    }

    @Override
    public boolean equals(Object other) {
        return
                other != null &&
                other instanceof Value &&
                !(((Value) other).getType() != null && getType() != null &&
                !((Value) other).getType().equals(getType())) &&
                ((Value) other).getValue().equals(getValue());
    }
}