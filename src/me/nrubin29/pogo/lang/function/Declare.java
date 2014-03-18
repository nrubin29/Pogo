package me.nrubin29.pogo.lang.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.Utils.Writable;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;
import me.nrubin29.pogo.lang.Variable.VariableType;

import java.util.Arrays;

public class Declare extends Function {

    public Declare() {
        super("declare");
    }

    /*
    Usage: declare(<variabletype>(:), <name>, [value(s)])
     */
    @Override
    public void run(Writable writable, Block b, String[] args, Variable receiver) throws InvalidCodeException {
        boolean isArray = args[0].endsWith(":");

        if (isArray) args[0] = args[0].substring(0, args[0].length() - 1);

        VariableType t = VariableType.match(args[0]);

        if (t == VariableType.VOID) throw new InvalidCodeException("Attempted to declare void variable.");

        String name = args[1];

        Object[] values = new String[args.length - 2];

        if (args.length >= 3) {
            values = Utils.implode(Arrays.copyOfRange(args, 2, args.length), b);
        }

        b.addVariable(t, name, isArray, values);
    }
}