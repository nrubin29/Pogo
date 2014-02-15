package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.PogoPlayer;

import java.util.ArrayList;

public class While extends ConditionalBlock {

    private final ArrayList<String> collection;

	public While(Block superBlock, String aVal, String bVal, CompareOperation compareOp, ArrayList<String> collection) {
        super(superBlock, aVal, bVal, compareOp);

        this.collection = new ArrayList<String>(collection);
	}

    public void run() throws InvalidCodeException {
        if (compareOp == ConditionalBlock.CompareOperation.EQUALS) {
            while (PogoPlayer.implode(new String[]{ aVal }, this).equals(PogoPlayer.implode(new String[] { bVal }, this))) {
                doLines(collection);
            }
        }

        else if (compareOp == CompareOperation.NOTEQUALS) {
            while (!PogoPlayer.implode(new String[]{ aVal }, this).equals(PogoPlayer.implode(new String[] { bVal }, this))) {
                doLines(collection);
            }
        }
    }
}