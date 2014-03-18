package me.nrubin29.pogo.lang.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Utils.Writable;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

abstract class Function {

    private final String name;

    Function(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void run(Writable writable, Block b, String[] args, Variable receiver) throws InvalidCodeException;

    @Override
    public String toString() {
        return "Function name=" + getName();
    }
}