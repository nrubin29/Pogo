package me.nrubin29.pogo.lang2;

public class Parameter implements Nameable {

    private String name;
    private Type value;

    public Parameter(String name, Type value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    public Type getValue() {
        return value;
    }

    public String toString() {
        return getClass() + " name=" + name + " value=" + value;
    }
}