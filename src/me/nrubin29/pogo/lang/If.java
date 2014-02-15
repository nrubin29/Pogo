package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.PogoPlayer;

public class If extends ConditionalBlock {

	public If(Block superBlock, String aVal, String bVal, ConditionalBlock.CompareOperation compareOp) {
        super(superBlock, aVal, bVal, compareOp);
	}

    public void runAfterParse() throws InvalidCodeException {
        if (compareOp == ConditionalBlock.CompareOperation.EQUALS) {
            if (PogoPlayer.implode(new String[] { aVal }, this).equals(PogoPlayer.implode(new String[] { bVal }, this))) {
                doBlocks();
            }
        }

        else if (compareOp == ConditionalBlock.CompareOperation.NOTEQUALS) {
            if (!PogoPlayer.implode(new String[] { aVal }, this).equals(PogoPlayer.implode(new String[] { bVal }, this))) {
                doBlocks();
            }
        }
    }
}