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

        else if (compareOp == CompareOperation.GREATERTHAN) {
            int a, b;

            do {
                try {
                    a = Integer.parseInt(PogoPlayer.implode(new String[]{ aVal }, this));
                    b = Integer.parseInt(PogoPlayer.implode(new String[] { bVal }, this));
                }
                catch (Exception e) {
                    throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers.");
                }

                doBlocks();
            } while (a > b);
        }

        else if (compareOp == CompareOperation.LESSTHAN) {
            int a, b;

            do {
                try {
                    a = Integer.parseInt(PogoPlayer.implode(new String[]{ aVal }, this));
                    b = Integer.parseInt(PogoPlayer.implode(new String[] { bVal }, this));
                }
                catch (Exception e) {
                    throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers.");
                }

                doBlocks();
            } while (a < b);
        }
    }
}