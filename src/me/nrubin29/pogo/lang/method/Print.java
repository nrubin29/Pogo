package me.nrubin29.pogo.lang.method;

import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.Utils.Writable;
import me.nrubin29.pogo.ide.Console.MessageType;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

public class Print extends SystemMethod {

    public Print() {
        super("print");
    }

    /*
    Usage: print(<message>)
     */
    @Override
    public void run(Writable writable, Block b, String[] args, Variable receiver) throws Utils.InvalidCodeException {
        writable.write(Utils.implode(args[0], b), MessageType.OUTPUT);
    }
}