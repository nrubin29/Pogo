package me.nrubin29.pogo.lang.systemmethod;

import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.IDEInstance;
import me.nrubin29.pogo.lang.Utils;
import me.nrubin29.pogo.lang.Utils.Writable;
import me.nrubin29.pogo.lang.Variable;

public class GetInput extends SystemMethod {

    public GetInput() {
        super("getinput");
    }

    /*
    Usage: getinput() <var>
     */
    @Override
    public void invoke(Block b, String[] params, Variable receiver) throws Utils.InvalidCodeException {
        if (receiver == null) return;

        Writable writable = IDEInstance.CURRENT_INSTANCE.getWritable();

        if (writable instanceof Console) receiver.setValue(((Console) writable).prompt());
        else receiver.setValue(Utils.prompt());
    }
}