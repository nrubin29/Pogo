package me.nrubin29.pogo.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

public class GetInput extends Function {

    public GetInput() {
        super("getinput");
    }

    /*
    Usage: getinput() <var>
     */
    @Override
    public void run(Console console, Block b, String[] args, Variable receiver) throws InvalidCodeException {
        String in = console.prompt();

        if (receiver != null) {
            receiver.getType().validateValue(in, b);
            receiver.setValue(in);
        }
    }
}