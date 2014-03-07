package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Pogo;

public class ElseIf extends ConditionalBlock {

    public ElseIf(Block superBlock, String aVal, String bVal, ConditionalBlock.CompareOperation compareOp) {
        super(superBlock, aVal, bVal, compareOp);
    }

    public boolean runElseIf() throws InvalidCodeException {
        boolean opSuccess = false;

        if (compareOp == ConditionalBlock.CompareOperation.EQUALS) {
            if (Pogo.implode(new String[]{aVal}, this).equals(Pogo.implode(new String[]{bVal}, this))) {
                doBlocks();
                opSuccess = true;
            }
        } else if (compareOp == ConditionalBlock.CompareOperation.NOTEQUALS) {
            if (!Pogo.implode(new String[]{aVal}, this).equals(Pogo.implode(new String[]{bVal}, this))) {
                doBlocks();
                opSuccess = true;
            }
        } else if (compareOp == CompareOperation.GREATERTHAN) {
            int a, b;

            try {
                a = Integer.parseInt(Pogo.implode(new String[]{aVal}, this));
                b = Integer.parseInt(Pogo.implode(new String[]{bVal}, this));
            } catch (Exception e) {
                throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers.");
            }

            if (a > b) {
                doBlocks();
                opSuccess = true;
            }
        } else if (compareOp == CompareOperation.LESSTHAN) {
            int a, b;

            try {
                a = Integer.parseInt(Pogo.implode(new String[]{aVal}, this));
                b = Integer.parseInt(Pogo.implode(new String[]{bVal}, this));
            } catch (Exception e) {
                throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers.");
            }

            if (a < b) {
                doBlocks();
                opSuccess = true;
            }
        }

        return opSuccess;
    }

    public void runAfterParse() throws InvalidCodeException {
        // We need to use runElseIf() because it returns a boolean.
    }
}