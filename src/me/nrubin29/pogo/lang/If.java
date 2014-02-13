package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

import java.util.ArrayList;

public class If extends Block {

    private ArrayList<String> collection;
    private Object aVal, bVal;
    private String compareOp;

	public If(Block superBlock, Object aVal, Object bVal, String compareOp, ArrayList<String> collection) {
        super(superBlock);

        this.aVal = aVal;
        this.bVal = bVal;
        this.compareOp = compareOp;
        this.collection = new ArrayList<String>(collection);
	}

    public void run() throws InvalidCodeException {
        /*
        TODO: Make compareOp an enum?
         */
        if (compareOp.equals("equals")) {
            if (aVal.equals(bVal)) {
                for (String line : collection) {
                    ((Class) getBlockTree()[0]).commandManager.parse(this, line);
                }
            }
        }
    }
}