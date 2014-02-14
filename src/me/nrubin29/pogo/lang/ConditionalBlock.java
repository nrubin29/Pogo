package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

public abstract class ConditionalBlock extends Block {

    public enum ConditionalBlockType {
        IF, WHILE;

        public static ConditionalBlockType match(String str) throws InvalidCodeException {
            for (ConditionalBlockType bt : values()) {
                if (bt.name().toLowerCase().equals(str)) return bt;
            }

            throw new InvalidCodeException("Condition block type " + str + " doesn't exist.");
        }
    }

    public enum CompareOperation {
        EQUALS;

        public static CompareOperation match(String str) throws InvalidCodeException {
            for (CompareOperation op : values()) {
                if (op.name().toLowerCase().equals(str)) return op;
            }

            throw new InvalidCodeException("Comparison operation " + str + " doesn't exist.");
        }
    }

    protected final String aVal;
    protected final String bVal;
    protected final ConditionalBlock.CompareOperation compareOp;

    public ConditionalBlock(Block superBlock, String aVal, String bVal, ConditionalBlock.CompareOperation compareOp) {
        super(superBlock);

        this.aVal = aVal;
        this.bVal = bVal;
        this.compareOp = compareOp;
    }

    public void run() throws InvalidCodeException {
        if (compareOp == ConditionalBlock.CompareOperation.EQUALS) {
            if (handleVarReferences(this, aVal).equals(handleVarReferences(this, bVal))) runWhenTrue();
        }
    }

    public abstract void runWhenTrue() throws InvalidCodeException;
}