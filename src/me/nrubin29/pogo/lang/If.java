package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

import java.util.ArrayList;

public class If extends ConditionalBlock {

    private final ArrayList<String> collection;

	public If(Block superBlock, String aVal, String bVal, ConditionalBlock.CompareOperation compareOp, ArrayList<String> collection) {
        super(superBlock, aVal, bVal, compareOp);

        this.collection = new ArrayList<String>(collection);
	}

    public void run() throws InvalidCodeException {
        if (compareOp == ConditionalBlock.CompareOperation.EQUALS) {
            if (handleVarReferences(this, aVal).equals(handleVarReferences(this, bVal))) {
                for (String line : collection) {
                    ((Class) getBlockTree()[0]).commandManager.parse(this, line);
                }
            }
        }
    }
}