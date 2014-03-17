package me.nrubin29.pogo.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

public class Random extends Function {

    public Random() {
        super("random");
    }

    private java.util.Random random;

    /*
    Usage: random([ceiling]) <var>
     */
    @Override
    public void run(Console console, Block b, String[] args, Variable receiver) throws InvalidCodeException {
        if (random == null) random = new java.util.Random();

        if (receiver != null) {
            if (receiver.getType() != Variable.VariableType.INTEGER && receiver.getType() != Variable.VariableType.DECIMAL) {
                throw new InvalidCodeException("Attempted to assign random number to non-number.");
            }

            int ceil = -1;

            if (!args[0].equals("")) {
                try {
                    ceil = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    throw new InvalidCodeException("Invalid ceiling.");
                }
            }

            if (receiver.getType() == Variable.VariableType.INTEGER) {
                if (ceil == -1) receiver.setValue(random.nextInt());
                else receiver.setValue(random.nextInt(ceil));
            } else if (receiver.getType() == Variable.VariableType.DECIMAL) {
                if (ceil == -1) receiver.setValue(random.nextDouble());
                else receiver.setValue(random.nextInt(ceil));
            }
        }
    }
}