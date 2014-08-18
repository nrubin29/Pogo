package me.nrubin29.pogo.lang.systemmethod;

import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Utils;
import me.nrubin29.pogo.lang.Variable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Math extends SystemMethod {

    private ScriptEngine engine;

    public Math() {
        super("math");
    }

    /*
    Usage: math(<expression>) <var>
     */
    @Override
    public void invoke(Block b, String[] params, Variable receiver) throws Utils.InvalidCodeException {
        if (engine == null) engine = new ScriptEngineManager().getEngineByName("JavaScript");

        if (receiver != null) {
            if (receiver.getType() != Variable.SystemVariableType.INTEGER && receiver.getType() != Variable.SystemVariableType.DECIMAL) {
                throw new Utils.InvalidCodeException("Attempted to assign math output to non-number.");
            }

            try {
                if (receiver.getType() == Variable.SystemVariableType.INTEGER) {
                    receiver.setValue(Integer.parseInt(engine.eval(Utils.implode(params[0], b)).toString()));
                } else receiver.setValue(Double.parseDouble(engine.eval(Utils.implode(params[0], b)).toString()));
            } catch (Exception e) {
                throw new Utils.InvalidCodeException("Invalid math expression.");
            }
        }
    }
}