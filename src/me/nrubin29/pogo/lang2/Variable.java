package me.nrubin29.pogo.lang2;

public class Variable implements Type, Nameable {

    private Block block;
    private String name;
    private Type type;
    private Object value;

    public Variable(Block block, String name, Type type) {
        this(block, name, type, null);
    }

    public Variable(Block block, String name, Type type, Object value) {
        this.block = block;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        if (type == PrimitiveType.BOOLEAN) {
            return Boolean.valueOf(value.toString());
        } else if (type == PrimitiveType.DOUBLE) {
            return Double.valueOf(value.toString());
        } else if (type == PrimitiveType.INTEGER) {
            return Integer.valueOf(value.toString());
        } else if (type == PrimitiveType.STRING) {
            return value.toString();
        } else {
            return value;
        }
    }

    public void setValue(Object value) {
        if (type == PrimitiveType.BOOLEAN) {
            this.value = Boolean.valueOf(value.toString());
        } else if (type == PrimitiveType.DOUBLE) {
            this.value = Double.valueOf(value.toString());
        } else if (type == PrimitiveType.INTEGER) {
            this.value = Integer.valueOf(value.toString());
        } else if (type == PrimitiveType.STRING) {
            this.value = value.toString();
        } else {
            this.value = value;
        }
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public String toString() {
        return getClass() + " name=" + name + " type=" + type + " value=" + value;
    }
}