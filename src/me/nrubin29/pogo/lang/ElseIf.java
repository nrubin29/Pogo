package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Utils;

public class ElseIf extends ConditionalBlock {

    public ElseIf(Block superBlock, String aVal, String bVal, ConditionalBlock.CompareOperation compareOp) {
        super(superBlock, aVal, bVal, compareOp);
    }

    public boolean runElseIf() throws InvalidCodeException {
        boolean opSuccess = false;

        if (compareOp == ConditionalBlock.CompareOperation.EQUALS) {
            if (Utils.implode(aVal, this).equals(Utils.implode(bVal, this))) {
                doBlocks();
                opSuccess = true;
            }
        } else if (compareOp == ConditionalBlock.CompareOperation.NOTEQUALS) {
            if (!Utils.implode(aVal, this).equals(Utils.implode(bVal, this))) {
                doBlocks();
                opSuccess = true;
            }
        } else if (compareOp == CompareOperation.GREATERTHAN) {
            double a, b;

            try {
                a = Double.valueOf(Utils.implode(aVal, this));
                b = Double.valueOf(Utils.implode(bVal, this));
            } catch (Exception e) {
                throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-numbers.");
            }

            if (a > b) {
                doBlocks();
                opSuccess = true;
            }
        } else if (compareOp == CompareOperation.LESSTHAN) {
            double a, b;

            try {
                a = Double.valueOf(Utils.implode(aVal, this));
                b = Double.valueOf(Utils.implode(bVal, this));
            } catch (Exception e) {
                throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-numbers.");
            }

            if (a < b) {
                doBlocks();
                opSuccess = true;
            }
        }

        return opSuccess;
    }

    @Override
    public void runAfterParse() throws InvalidCodeException {
        // We need to use runElseIf() because it returns a boolean.
    }

    @Override
    public String toString() {
        return "ElseIf aVal=" + aVal + " bVal=" + bVal + " compareOp=" + compareOp.name();
    }
}