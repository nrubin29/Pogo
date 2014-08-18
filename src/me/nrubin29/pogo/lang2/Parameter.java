package me.nrubin29.pogo.lang2;

public class Parameter implements Nameable {

    private Type type;
    private String name;

    public Parameter(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        return getClass() + " name=" + name + " type=" + type;
    }
}