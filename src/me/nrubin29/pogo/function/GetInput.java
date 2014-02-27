package me.nrubin29.pogo.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.gui.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

public class GetInput extends Function {

	public GetInput() {
		super("getinput");
	}

    /*
     * Usage: getinput <variable>
     */
	public void run(Console console, Block b, String[] args) throws InvalidCodeException {
        Variable v = b.getVariable(args[0]);
        String in = console.prompt();

        v.getType().validateValue(in);
        v.setValue(in);
	}
}