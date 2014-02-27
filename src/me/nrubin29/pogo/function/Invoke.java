package me.nrubin29.pogo.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.gui.Console;
import me.nrubin29.pogo.lang.Block;

public class Invoke extends Function {

	public Invoke() {
		super("invoke");
	}
	
	/*
	 * Usage: invoke <method>
	 */
	public void run(Console console, Block b, String[] args) throws InvalidCodeException {
        ((me.nrubin29.pogo.lang.Class) b.getBlockTree()[0]).getMethod(args[0]).run();
	}
}