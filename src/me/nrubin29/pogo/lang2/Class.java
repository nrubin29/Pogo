package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.Pogo;
import me.nrubin29.pogo.ide.Console;

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
    public void run() throws InvalidCodeException {
        Pogo.getIDE().getConsole().write("run() called on " + toString(), Console.MessageType.OUTPUT);

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
