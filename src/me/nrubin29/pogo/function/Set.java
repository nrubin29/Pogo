package me.nrubin29.pogo.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.PogoPlayer;
import me.nrubin29.pogo.gui.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

import java.util.Arrays;

public class Set extends Function {

	public Set() {
		super("set");
	}
	
	/*
	 * Usage: set <variable> <value>
	 */
	public void run(Console console, Block b, String[] args) throws InvalidCodeException {
        Variable v = b.getVariable(args[0]);

        if (v.getType() != Variable.VariableType.STRING) {
            v.getType().validateValue(args[3]);
            v.setValue(args[3]);
        }

		else v.setValue(PogoPlayer.implode(Arrays.copyOfRange(args, 1, args.length), b));
	}
}