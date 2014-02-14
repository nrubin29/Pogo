package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.gui.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

import java.util.Arrays;

public class Set extends Command {

	public Set() {
		super("set");
	}
	
	/*
	 * set str Hello
	 */
	public void run(Console console, Block b, String[] args) throws InvalidCodeException {
        Variable v = b.getVariable(args[0]);

        if (v.getType() != Variable.VariableType.STRING) {
            v.getType().validateValue(args[3]);
            v.setValue(args[3]);
        }

		else {
            StringBuilder newVal = new StringBuilder();

            for (String str : Arrays.copyOfRange(args, 1, args.length)) {
                newVal.append(str).append(" ");
            }

            v.setValue(newVal.toString());
        }
	}
}