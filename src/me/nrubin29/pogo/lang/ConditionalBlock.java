package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

public abstract class ConditionalBlock extends Block {

    public enum ConditionalBlockType {
        IF, ELSEIF, ELSE, WHILE
    }

    public enum CompareOperation {
        EQUALS("="), NOTEQUALS("!="), GREATERTHAN(">"), LESSTHAN("<");

        private final String op;

        CompareOperation(String op) {
            this.op = op;
        }

        public String getOp() {
            return op;
        }

        public static CompareOperation match(String str) throws InvalidCodeException {
            for (CompareOperation op : values()) {
                if (op.getOp().equals(str)) return op;
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

    public abstract void runAfterParse() throws InvalidCodeException;
}