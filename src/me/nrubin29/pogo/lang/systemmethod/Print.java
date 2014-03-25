package me.nrubin29.pogo.lang.systemmethod;

import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.ide.Console.MessageType;
import me.nrubin29.pogo.ide.Instance;
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
    public void invoke(Block b, String[] params, Variable receiver) throws Utils.InvalidCodeException {
        Instance.CURRENT_INSTANCE.getWritable().write(Utils.implode(params[0], b), MessageType.OUTPUT);
    }
}