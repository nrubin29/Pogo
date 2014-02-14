package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

import java.util.ArrayList;

public class If extends Block {

    public enum CompareOperation {
        EQUALS;

        public static CompareOperation match(String str) throws InvalidCodeException {
            for (CompareOperation op : values()) {
                if (op.name().toLowerCase().equals(str)) return op;
            }

            throw new InvalidCodeException("Comparison operation " + str + " doesn't exist.");
        }
    }

    private ArrayList<String> collection;
    private String aVal, bVal;
    private CompareOperation compareOp;

	public If(Block superBlock, String aVal, String bVal, CompareOperation compareOp, ArrayList<String> collection) {
        super(superBlock);

        this.aVal = aVal;
        this.bVal = bVal;
        this.compareOp = compareOp;
        this.collection = new ArrayList<String>(collection);
	}

    public void run() throws InvalidCodeException {
        if (compareOp == CompareOperation.EQUALS) {
            if (handleVarReferences(this, aVal).equals(handleVarReferences(this, bVal))) {
                for (String line : collection) {
                    ((Class) getBlockTree()[0]).commandManager.parse(this, line);
                }
            }
        }
    }
}