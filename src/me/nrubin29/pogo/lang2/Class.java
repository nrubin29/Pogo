package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.ide.Console;

import java.io.IOException;

public class Class extends Block implements Type, Nameable, Cloneable {

    private String name;

    public Class(String name) {
        super(null);

        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Runtime.RUNTIME.print("run() called on " + toString(), Console.MessageType.OUTPUT);

        getSubBlock(Method.class, "main").get().run();
    }

    @Override
    public String toString() {
        return getClass() + " name=" + name;
    }

    @Override
    public Class clone() {
        Class clazz = new Class(name);
        cloneHelp(super.clone());
        return clazz;
    }
}
