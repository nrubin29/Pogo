package me.nrubin29.pogo.lang.function;

import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

public class GetInput extends SystemMethod {

    public GetInput() {
        super("getinput");
    }

    /*
    Usage: getinput() <var>
     */
    @Override
    public void run(Utils.Writable writable, Block b, String[] args, Variable receiver) throws Utils.InvalidCodeException {
        if (receiver == null) return;

        if (writable instanceof Console) receiver.setValue(((Console) writable).prompt());
        else receiver.setValue(Utils.prompt());
    }
}