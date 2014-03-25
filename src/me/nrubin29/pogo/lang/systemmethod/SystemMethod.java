package me.nrubin29.pogo.lang.systemmethod;

import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Method.Visibility;
import me.nrubin29.pogo.lang.Variable;

abstract class SystemMethod implements Utils.Invokable {

    private final String name;

    SystemMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public abstract void invoke(Block b, String[] params, Variable receiver) throws Utils.InvalidCodeException;

    @Override
    public Visibility getVisibility() {
        return Visibility.PUBLIC;
    }

    @Override
    public String toString() {
        return "SystemMethod name=" + getName();
    }
}