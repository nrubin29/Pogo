package me.nrubin29.pogo.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.PogoPlayer;
import me.nrubin29.pogo.gui.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Arrays;

public class Math extends Function {

	public Math() {
		super("math");
	}

    private ScriptEngine engine;
	
	/*
	 * Usage: math <variable> <expression>
	 */
	public void run(Console console, Block b, String[] args) throws InvalidCodeException {
        if (engine == null) engine = new ScriptEngineManager().getEngineByName("JavaScript");

        Variable v = b.getVariable(args[0]);

        if (v.getType() != Variable.VariableType.INTEGER) {
            throw new InvalidCodeException("Attempted to assign math output to non-integer.");
        }

        try {
            v.setValue(new Double(Double.parseDouble(engine.eval(PogoPlayer.implode(Arrays.copyOfRange(args, 1, args.length), b)).toString())).intValue());
        }
        catch (Exception e) { throw new InvalidCodeException("Invalid math expression."); }
	}
}