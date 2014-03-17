package me.nrubin29.pogo.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.ide.Console.MessageType;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

public class Print extends Function {

    public Print() {
        super("print");
    }

    /*
    Usage: print(<message>)
     */
    @Override
    public void run(Console console, Block b, String[] args, Variable receiver) throws InvalidCodeException {
        console.write(Utils.implode(args[0], b), MessageType.OUTPUT);
    }
}