package me.nrubin29.pogo.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Pogo;
import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

public class Set extends Function {

    public Set() {
        super("set");
    }

    /*
     * Usage: set(<value>) <var>
     */
    public void run(Console console, Block b, String[] args, Variable receiver) throws InvalidCodeException {
        if (receiver == null) throw new InvalidCodeException("Attempted to set variable but no variable specified.");

        receiver.getType().validateValue(args[0], b);
        receiver.setValue(Pogo.implode(new String[]{args[0]}, b));
    }
}