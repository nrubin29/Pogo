package me.nrubin29.pogo.lang.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.Utils.Writable;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Math extends Function {

    private ScriptEngine engine;

    public Math() {
        super("math");
    }

    /*
    Usage: math(<expression>) <var>
     */
    @Override
    public void run(Writable writable, Block b, String[] args, Variable receiver) throws InvalidCodeException {
        if (engine == null) engine = new ScriptEngineManager().getEngineByName("JavaScript");

        if (receiver != null) {
            if (receiver.getType() != Variable.VariableType.INTEGER && receiver.getType() != Variable.VariableType.DECIMAL) {
                throw new InvalidCodeException("Attempted to assign math output to non-number.");
            }

            try {
                if (receiver.getType() == Variable.VariableType.INTEGER) {
                    receiver.setValue(Integer.parseInt(engine.eval(Utils.implode(args[0], b)).toString()));
                } else receiver.setValue(Double.parseDouble(engine.eval(Utils.implode(args[0], b)).toString()));
            } catch (Exception e) {
                throw new InvalidCodeException("Invalid math expression.");
            }
        }
    }
}