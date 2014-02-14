package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

import java.util.ArrayList;

public class While extends ConditionalBlock {

    private final ArrayList<String> collection;

	public While(Block superBlock, String aVal, String bVal, CompareOperation compareOp, ArrayList<String> collection) {
        super(superBlock, aVal, bVal, compareOp);

        this.collection = new ArrayList<String>(collection);
	}

    @Override
    public void run() throws InvalidCodeException {
        if (compareOp == ConditionalBlock.CompareOperation.EQUALS) {
            while (handleVarReferences(this, aVal).equals(handleVarReferences(this, bVal))) {
                runWhenTrue();
            }
        }
    }

    public void runWhenTrue() throws InvalidCodeException {
        for (String line : collection) {
            ((Class) getBlockTree()[0]).commandManager.parse(this, line);
        }
    }
}