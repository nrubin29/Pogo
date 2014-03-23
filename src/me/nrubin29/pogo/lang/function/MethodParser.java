package me.nrubin29.pogo.lang.function;

import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.Utils.Writable;
import me.nrubin29.pogo.ide.Instance;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Class;
import me.nrubin29.pogo.lang.Method;
import me.nrubin29.pogo.lang.Variable;
import me.nrubin29.pogo.lang.Variable.VariableType;

import java.util.ArrayList;

public class MethodParser {

    private final ArrayList<SystemMethod> systemMethods = new ArrayList<SystemMethod>();

    private final Writable writable;

    public MethodParser(Writable writable) {
        this.writable = writable;

        systemMethods.add(new Declare());
        systemMethods.add(new GetInput());
        systemMethods.add(new Math());
        systemMethods.add(new Print());
        systemMethods.add(new Random());
        systemMethods.add(new Set());
    }

    /*
    class.method(arg0, arg1) var
    class is the name of class that contains the method. All system methods are contained in the system class.
    var is the variable that receives the output of the method (if it is not void).
     */
    public void parse(Block b, String input) throws Utils.InvalidCodeException {
        String clazz = input.substring(0, input.indexOf(".")), method = input.substring(input.indexOf(".") + 1, input.indexOf("(")).trim();

        String[] args = Utils.changeCommas(input.substring(input.indexOf("(") + 1, input.indexOf(")"))).split(",");

        for (int i = 0; i < args.length; i++) {
            args[i] = Utils.unchangeCommas(args[i]);
        }

        if (args.length == 1 && args[0].equals("")) {
            args = new String[0];
        }

        Variable receiver = null;

        try {
            receiver = b.getVariable(input.substring(input.indexOf(")") + 1).trim());
        } catch (Utils.InvalidCodeException ignored) {
        }

        if (clazz.equals("system")) {
            SystemMethod met = null;

            for (SystemMethod m : systemMethods) {
                if (m.getName().equals(method)) met = m;
            }

            if (met == null) throw new Utils.InvalidCodeException("SystemMethod " + method + " does not exist.");

            else met.run(writable, b, args, receiver);
        } else {
            Method nonSystemMethod;

            if (clazz.equals("this")) nonSystemMethod = ((Class) b.getBlockTree()[0]).getMethod(method);
            else nonSystemMethod = Instance.CURRENT_INSTANCE.getPogoClass(clazz).getMethod(method);

            Object retValue = nonSystemMethod.invoke(args);
            if (receiver != null) {
                if (nonSystemMethod.getReturnType() == VariableType.VOID) {
                    throw new Utils.InvalidCodeException("Attempted to store result of void method to variable.");
                }
                receiver.setValue(retValue);
            }
        }
    }
}