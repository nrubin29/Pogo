package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.Block;
import me.nrubin29.pogo.GUI;
import me.nrubin29.pogo.InvalidCodeException;

public class Invoke extends Command {

	public Invoke() {
		super("invoke");
	}
	
	/*
	 * invoke method
	 */
	public void run(GUI gui, Block b, String[] args) throws InvalidCodeException {
        b.getAncestorClass().getMethod(args[0]).run();
	}
}