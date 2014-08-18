package me.nrubin29.pogo.lang.systemmethod;

import me.nrubin29.pogo.lang.*;
import me.nrubin29.pogo.lang.Class;
import me.nrubin29.pogo.lang.Utils.InvalidCodeException;
import me.nrubin29.pogo.lang.Utils.Invokable;

import java.util.ArrayList;

public class MethodParser {

    private final ArrayList<SystemMethod> systemMethods = new ArrayList<>();

    public MethodParser() {
        systemMethods.add(new Declare());
        systemMethods.add(new GetInput());
        systemMethods.add(new Math());
        systemMethods.add(new Print());
        systemMethods.add(new Random());
        systemMethods.add(new Set());
    }

    /*
    class.systemmethod(arg0, arg1) var
    class is the name of class that contains the systemmethod. All system methods are contained in the system class.
    var is the variable that receives the output of the systemmethod (if it is not void).
     */
    public void parse(Block b, String input) throws Utils.InvalidCodeException {
        String invoked = input.substring(0, input.indexOf(".")), method = input.substring(input.indexOf(".") + 1, input.indexOf("(")).trim();

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

        Invokable toInvoke = null;
        boolean isVar = false, success = false;

        if (invoked.equals("system")) {
            for (SystemMethod m : systemMethods) {
                if (m.getName().equals(method)) {
                    toInvoke = m;
                    success = true;
                }
            }

            if (toInvoke == null) {
                throw new Utils.InvalidCodeException("System systemmethod " + method + " does not exist.");
            }
        } else {
            if (b.hasVariable(invoked)) {
                Variable invokedVar = b.getVariable(invoked);
                if (invokedVar.getType() instanceof Class) {
                    Class c = (Class) invokedVar.getType();
                    toInvoke = c.getMethod(method);
                    isVar = true;
                } else throw new InvalidCodeException("Attempted to invoke systemmethod on primitive data type.");
            } else {
                if (invoked.equals("this")) {
                    toInvoke = ((Class) b.getBlockTree()[0]).getMethod(method);
                    success = true;
                } else {
                    Class c = IDEInstance.CURRENT_INSTANCE.getPogoClass(invoked);
                    if (c == null) throw new InvalidCodeException("Could not find class with name " + invoked + ".");
                    toInvoke = c.getMethod(method);
                }
            }
        }

        if (isVar && toInvoke.getVisibility() != Method.Visibility.PRIVATE) success = true;
        else if (!isVar && toInvoke.getVisibility() == Method.Visibility.PUBLIC) success = true;

        if (!success) {
            throw new InvalidCodeException("Method " + method + " in " + invoked + " is not accessible from " + ((Class) b.getBlockTree()[0]).name() + ".");
        }

        toInvoke.invoke(b, args, receiver);
    }
}