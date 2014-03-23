package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.Utils;

public class ElseIf extends ConditionalBlock {

    public ElseIf(Block superBlock, String aVal, String bVal, ConditionalBlock.CompareOperation compareOp) {
        super(superBlock, aVal, bVal, compareOp);
    }

    public boolean runElseIf() throws Utils.InvalidCodeException {
        boolean opSuccess = false;

        if (compareOp == ConditionalBlock.CompareOperation.EQUALS) {
            if (Utils.implode(aVal, this).equals(Utils.implode(bVal, this))) {
                super.run();
                opSuccess = true;
            }
        } else if (compareOp == ConditionalBlock.CompareOperation.NOTEQUALS) {
            if (!Utils.implode(aVal, this).equals(Utils.implode(bVal, this))) {
                super.run();
                opSuccess = true;
            }
        } else if (compareOp == CompareOperation.GREATERTHAN) {
            double a, b;

            try {
                a = Double.valueOf(Utils.implode(aVal, this));
                b = Double.valueOf(Utils.implode(bVal, this));
            } catch (Exception e) {
                throw new Utils.InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-numbers.");
            }

            if (a > b) {
                super.run();
                opSuccess = true;
            }
        } else if (compareOp == CompareOperation.LESSTHAN) {
            double a, b;

            try {
                a = Double.valueOf(Utils.implode(aVal, this));
                b = Double.valueOf(Utils.implode(bVal, this));
            } catch (Exception e) {
                throw new Utils.InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-numbers.");
            }

            if (a < b) {
                super.run();
                opSuccess = true;
            }
        }

        return opSuccess;
    }

    @Override
    public String toString() {
        return "ElseIf aVal=" + aVal + " bVal=" + bVal + " compareOp=" + compareOp.name();
    }
}