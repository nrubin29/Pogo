package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.PogoPlayer;

public class While extends ConditionalBlock {

	public While(Block superBlock, String aVal, String bVal, CompareOperation compareOp) {
        super(superBlock, aVal, bVal, compareOp);
	}

    public void runAfterParse() throws InvalidCodeException {
        if (compareOp == ConditionalBlock.CompareOperation.EQUALS) {
            while (PogoPlayer.implode(new String[]{ aVal }, this).equals(PogoPlayer.implode(new String[] { bVal }, this))) {
                doBlocks();
            }
        }

        else if (compareOp == CompareOperation.NOTEQUALS) {
            while (!PogoPlayer.implode(new String[]{ aVal }, this).equals(PogoPlayer.implode(new String[] { bVal }, this))) {
                doBlocks();
            }
        }
    }
}