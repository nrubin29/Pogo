package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.Class;
import me.nrubin29.pogo.GUI;
import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Method;
import me.nrubin29.pogo.Variable.VariableType;

public class Declare extends Command {

	public Declare() {
		super("declare");
	}
	
	/*
	 * declare string str = Hello
	 */
	public void run(GUI gui, Class c, Method m, String[] args) throws InvalidCodeException {
		VariableType t = VariableType.match(args[0]);
		String name = args[1];
		Object value = args[3];
		
		c.addVariable(t, name, value);
	}
}