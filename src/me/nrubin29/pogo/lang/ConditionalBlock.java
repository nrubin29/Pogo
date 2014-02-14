package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

import java.util.ArrayList;

public abstract class ConditionalBlock extends Block {

    public enum ConditionalBlockType {
        IF, WHILE
    }

    public enum CompareOperation {
        EQUALS, NOTEQUALS;

        public static CompareOperation match(String str) throws InvalidCodeException {
            for (CompareOperation op : values()) {
                if (op.name().toLowerCase().equals(str)) return op;
            }

            throw new InvalidCodeException("Comparison operation " + str + " doesn't exist.");
        }
    }

    final String aVal;
    final String bVal;
    final ConditionalBlock.CompareOperation compareOp;

    ConditionalBlock(Block superBlock, String aVal, String bVal, ConditionalBlock.CompareOperation compareOp) {
        super(superBlock);

        this.aVal = aVal;
        this.bVal = bVal;
        this.compareOp = compareOp;
    }

    public abstract void run() throws InvalidCodeException;

    public final void doLines(ArrayList<String> collection) throws InvalidCodeException {
        for (String line : collection) {
            ((Class) getBlockTree()[0]).commandManager.parse(this, line);
        }
    }
}