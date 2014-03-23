package me.nrubin29.pogo.lang.function;

import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.Utils.Writable;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

abstract class SystemMethod {

    private final String name;

    SystemMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void run(Writable writable, Block b, String[] args, Variable receiver) throws Utils.InvalidCodeException;

    @Override
    public String toString() {
        return "SystemMethod name=" + getName();
    }
}