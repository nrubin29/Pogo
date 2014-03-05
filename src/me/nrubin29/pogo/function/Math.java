package me.nrubin29.pogo.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Pogo;
import me.nrubin29.pogo.gui.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Math extends Function {

    public Math() {
        super("math");
    }

    private ScriptEngine engine;

    /*
    Usage: math(<expression>) <var>
     */
    public void run(Console console, Block b, String[] args, Variable receiver) throws InvalidCodeException {
        if (engine == null) engine = new ScriptEngineManager().getEngineByName("JavaScript");

        if (receiver != null) {
            if (receiver.getType() != Variable.VariableType.INTEGER && receiver.getType() != Variable.VariableType.DECIMAL) {
                throw new InvalidCodeException("Attempted to assign math output to non-number.");
            }

        	/*
        	Note: This needs to set an integer type to integer value of decimal type to decimal value (1.5 invalid for integer)
        	 */
            try {
                receiver.setValue(Double.parseDouble(engine.eval(Pogo.implode(new String[]{args[0]}, b)).toString()));
            } catch (Exception e) {
                throw new InvalidCodeException("Invalid math expression.");
            }
        }
    }
}