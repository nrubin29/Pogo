package me.nrubin29.pogo.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.PogoPlayer;
import me.nrubin29.pogo.gui.Console;
import me.nrubin29.pogo.lang.Block;

public class Print extends Function {

	public Print() {
		super("print");
	}
	
	/*
	 * Usage: print <message>
	 */
	public void run(Console console, Block b, String[] args) throws InvalidCodeException {
        console.write(PogoPlayer.implode(args, b));
	}
}