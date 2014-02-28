package me.nrubin29.pogo.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.gui.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Method;
import me.nrubin29.pogo.lang.Variable;
import me.nrubin29.pogo.lang.Variable.VariableType;

public class Invoke extends Function {

	public Invoke() {
		super("invoke");
	}
	
	/*
	 * Usage: invoke <method> [variable]
	 */
	public void run(Console console, Block b, String[] args) throws InvalidCodeException {
		Method m = ((me.nrubin29.pogo.lang.Class) b.getBlockTree()[0]).getMethod(args[0]);
		
		if (m.getReturnType() == VariableType.VOID) throw new InvalidCodeException("Attempted to store void to variable.");
		
		Variable v = null;
		
		if (args.length > 1) v = b.getVariable(args[1]);
		
        m.run();
		Object ret = m.invoke();
		
		if (v != null) {
			v.getType().validateValue(ret, b);
			v.setValue(ret);
		}
	}
}