package me.nrubin29.pogo.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Pogo;
import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

public class Print extends Function {

    public Print() {
        super("print");
    }

    /*
    Usage: print(<message>)
     */
    public void run(Console console, Block b, String[] args, Variable receiver) throws InvalidCodeException {
        console.write(Pogo.implode(new String[]{args[0]}, b));
    }
}