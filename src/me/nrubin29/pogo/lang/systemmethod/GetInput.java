package me.nrubin29.pogo.lang.systemmethod;

import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.Utils.Writable;
import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.ide.Instance;
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
    public void invoke(Block b, String[] params, Variable receiver) throws Utils.InvalidCodeException {
        if (receiver == null) return;

        Writable writable = Instance.CURRENT_INSTANCE.getWritable();

        if (writable instanceof Console) receiver.setValue(((Console) writable).prompt());
        else receiver.setValue(Utils.prompt());
    }
}