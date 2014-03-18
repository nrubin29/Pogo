package me.nrubin29.pogo.lang.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.Utils.Writable;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Class;
import me.nrubin29.pogo.lang.Method;
import me.nrubin29.pogo.lang.Variable;
import me.nrubin29.pogo.lang.Variable.VariableType;

import java.util.ArrayList;

public class FunctionManager {

    private final ArrayList<Function> functs = new ArrayList<Function>();

    private final Writable writable;

    public FunctionManager(Writable writable) {
        this.writable = writable;

        functs.add(new Declare());
        functs.add(new GetInput());
        functs.add(new Math());
        functs.add(new Print());
        functs.add(new Random());
        functs.add(new Set());
    }

    /*
    method(arg0, arg1) var // var is the variable that receives the output of the method.
     */
    public void parse(Block b, String input) throws InvalidCodeException {
        String funct = input.substring(0, input.indexOf("(")).trim();

        String[] args = Utils.changeCommas(input.substring(input.indexOf("(") + 1, input.indexOf(")"))).split(",");

        for (int i = 0; i < args.length; i++) {
            args[i] = Utils.unchangeCommas(args[i]);
        }

        Variable receiver = null;

        try {
            receiver = b.getVariable(input.substring(input.indexOf(")") + 1).trim());
        } catch (InvalidCodeException ignored) {
        }

        try {
            Method method = ((Class) b.getBlockTree()[0]).getMethod(funct);
            Object retValue = method.invoke(args);
            if (receiver != null) {
                if (method.getReturnType() == VariableType.VOID) {
                    throw new InvalidCodeException("Attempted to store result of void method to variable.");
                }
                receiver.setValue(retValue);
            }
        } catch (InvalidCodeException e) {
            Function fun = null;

            for (Function f : functs) {
                if (f.getName().equals(funct)) fun = f;
            }

            if (fun == null) throw new InvalidCodeException("Function " + funct + " does not exist.");

            else fun.run(writable, b, args, receiver);
        }
    }
}