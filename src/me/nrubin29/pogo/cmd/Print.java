package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.gui.GUI;
import me.nrubin29.pogo.lang.Block;

public class Print extends Command {

	public Print() {
		super("print");
	}
	
	public void run(GUI gui, Block b, String[] args) throws InvalidCodeException {
		StringBuilder msg = new StringBuilder();
		
		for (String str : args) msg.append(str + " ");

        gui.write(b.handleVarReferences(b, msg.toString()));
	}
}