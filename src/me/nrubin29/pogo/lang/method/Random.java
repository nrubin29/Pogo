package me.nrubin29.pogo.lang.method;

import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.Utils.Writable;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

public class Random extends SystemMethod {

    private java.util.Random random;

    public Random() {
        super("random");
    }

    /*
    Usage: random([ceiling]) <var>
     */
    @Override
    public void run(Writable writable, Block b, String[] args, Variable receiver) throws Utils.InvalidCodeException {
        if (random == null) random = new java.util.Random();

        if (receiver != null) {
            if (receiver.getType() != Variable.SystemVariableType.INTEGER && receiver.getType() != Variable.SystemVariableType.DECIMAL) {
                throw new Utils.InvalidCodeException("Attempted to assign random number to non-number.");
            }

            int ceil = -1;

            if (!args[0].equals("")) {
                try {
                    ceil = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    throw new Utils.InvalidCodeException("Invalid ceiling.");
                }
            }

            if (receiver.getType() == Variable.SystemVariableType.INTEGER) {
                if (ceil == -1) receiver.setValue(random.nextInt());
                else receiver.setValue(random.nextInt(ceil));
            } else if (receiver.getType() == Variable.SystemVariableType.DECIMAL) {
                if (ceil == -1) receiver.setValue(random.nextDouble());
                else receiver.setValue(random.nextInt(ceil));
            }
        }
    }
}