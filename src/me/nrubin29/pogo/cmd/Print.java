package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.Block;
import me.nrubin29.pogo.GUI;
import me.nrubin29.pogo.InvalidCodeException;

public class Print extends Command {

	public Print() {
		super("print");
	}
	
	public void run(GUI gui, Block b, String[] args) throws InvalidCodeException {
		String msg = "";
		
		for (String str : args) {
			if (str.startsWith("_")) msg += b.getVariable(str.substring(1)).getValue() + " ";
			else msg += str.replaceAll("~", " ") + " ";
		}

        gui.write(msg);
	}
}