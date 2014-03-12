package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Utils;

import java.util.ArrayList;

public class If extends ConditionalBlock {

    private final ArrayList<ElseIf> elzeIfs;
    private Else elze;

    public If(Block superBlock, String aVal, String bVal, ConditionalBlock.CompareOperation compareOp) {
        super(superBlock, aVal, bVal, compareOp);

        this.elzeIfs = new ArrayList<ElseIf>();
    }

    public void addElseIf(ElseIf elzeIf) {
        elzeIfs.add(elzeIf);
    }

    public void setElse(Else elze) {
        this.elze = elze;
    }

    @Override
    public void runAfterParse() throws InvalidCodeException {
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

        if (!opSuccess) {
            boolean elzeIfRan = false;

            for (ElseIf elzeIf : elzeIfs) {
                elzeIf.run();
                if (elzeIf.runElseIf()) {
                    elzeIfRan = true;
                    break;
                }
            }

            if (!elzeIfRan && elze != null) {
                elze.run();
                elze.doBlocks();
            }
        }
    }

    @Override
    public String toString() {
        return "If aVal=" + aVal + " bVal=" + bVal + " compareOp=" + compareOp.name();
    }
}