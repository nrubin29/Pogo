package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Utils;

public class While extends ConditionalBlock {

    public While(Block superBlock, String aVal, String bVal, CompareOperation compareOp) {
        super(superBlock, aVal, bVal, compareOp);
    }

    @Override
    public void runAfterParse() throws InvalidCodeException {
        if (compareOp == ConditionalBlock.CompareOperation.EQUALS) {
            double a, b;

            do {
                try {
                    a = Double.valueOf(Utils.implode(aVal, this));
                    b = Double.valueOf(Utils.implode(bVal, this));
                } catch (Exception e) {
                    throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-numbers.");
                }

                doBlocks();
            } while (a == b);
        } else if (compareOp == CompareOperation.NOTEQUALS) {
            double a, b;

            do {
                try {
                    a = Double.valueOf(Utils.implode(aVal, this));
                    b = Double.valueOf(Utils.implode(bVal, this));
                } catch (Exception e) {
                    throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-numbers.");
                }

                doBlocks();
            } while (a != b);
        } else if (compareOp == CompareOperation.GREATERTHAN) {
            double a, b;

            do {
                try {
                    a = Double.valueOf(Utils.implode(aVal, this));
                    b = Double.valueOf(Utils.implode(bVal, this));
                } catch (Exception e) {
                    throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-numbers.");
                }

                doBlocks();
            } while (a > b);
        } else if (compareOp == CompareOperation.LESSTHAN) {
            double a, b;

            do {
                try {
                    a = Double.valueOf(Utils.implode(aVal, this));
                    b = Double.valueOf(Utils.implode(bVal, this));
                } catch (Exception e) {
                    throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-numbers.");
                }

                doBlocks();
            } while (a < b);
        }
    }

    @Override
    public String toString() {
        return "While aVal=" + aVal + " bVal=" + bVal + " compareOp=" + compareOp.name();
    }
}