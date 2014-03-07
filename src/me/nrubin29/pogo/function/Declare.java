package me.nrubin29.pogo.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Pogo;
import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;
import me.nrubin29.pogo.lang.Variable.VariableType;

public class Declare extends Function {

    public Declare() {
        super("declare");
    }

    /*
    Usage: declare(<variabletype>, <name>, <value>)
     */
    public void run(Console console, Block b, String[] args, Variable receiver) throws InvalidCodeException {
        VariableType t = VariableType.match(args[0]);

        if (t == VariableType.VOID) throw new InvalidCodeException("Attempted to declare void variable.");

        String name = args[1];

        Object value;

        if (t == VariableType.STRING) {
            value = Pogo.implode(new String[]{args[2]}, b);
        } else {
            t.validateValue(args[2], b);
            value = args[2];
        }

        b.addVariable(t, name, value);
    }
}