package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.PogoPlayer;

import java.util.ArrayList;

public class If extends ConditionalBlock {

    private final ArrayList<String> collection;

	public If(Block superBlock, String aVal, String bVal, ConditionalBlock.CompareOperation compareOp, ArrayList<String> collection) {
        super(superBlock, aVal, bVal, compareOp);

        this.collection = new ArrayList<String>(collection);
	}

    public void run() throws InvalidCodeException {
        if (compareOp == ConditionalBlock.CompareOperation.EQUALS) {
            if (PogoPlayer.implode(new String[] { aVal }, this).equals(PogoPlayer.implode(new String[] { bVal }, this))) {
                doLines(collection);
            }
        }

        else if (compareOp == ConditionalBlock.CompareOperation.NOTEQUALS) {
            if (!PogoPlayer.implode(new String[] { aVal }, this).equals(PogoPlayer.implode(new String[] { bVal }, this))) {
                doLines(collection);
            }
        }
    }
}