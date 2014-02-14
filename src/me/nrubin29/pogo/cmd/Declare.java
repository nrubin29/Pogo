package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.gui.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable.VariableType;

public class Declare extends Command {

	public Declare() {
		super("declare");
	}
	
	/*
	 * declare string str = Hello
	 */
	public void run(Console console, Block b, String[] args) throws InvalidCodeException {
        VariableType t = VariableType.match(args[0]);
		String name = args[1];

        Object value = null;

        if (args.length == 4) {
            t.validateValue(args[3]);
            value = args[3];
        }
		
		b.addVariable(t, name, value);
	}
}