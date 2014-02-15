package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.gui.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

public class Random extends Command {

	public Random() {
		super("random");
	}

    private java.util.Random random;

	/*
	 * random varname [ceiling]
	 */
	public void run(Console console, Block b, String[] args) throws InvalidCodeException {
        if (random == null) random = new java.util.Random();

        Variable v = b.getVariable(args[0]);

        if (v.getType() != Variable.VariableType.INTEGER) {
            throw new InvalidCodeException("Attempted to assign random number to non-integer.");
        }

        int ceil = -1;

        if (args.length == 2) {
            try { ceil = Integer.parseInt(args[1]); }
            catch (Exception e) { throw new InvalidCodeException("Invalid ceiling."); }
        }

        if (ceil == -1) v.setValue(random.nextInt());
        else v.setValue(random.nextInt(ceil));
	}
}