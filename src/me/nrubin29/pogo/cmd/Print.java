package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.Class;
import me.nrubin29.pogo.GUI;
import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Method;

public class Print extends Command {

	public Print() {
		super("print");
	}
	
	public void run(GUI gui, Class c, Method m, String[] args) throws InvalidCodeException {
		String msg = "";
		
		for (String str : args) {
			if (str.startsWith("_")) msg += c.getVariable(str.substring(1)).getValue();
			else msg += str.replaceAll("~", " ");
		}
		
		gui.write(msg);
	}
}