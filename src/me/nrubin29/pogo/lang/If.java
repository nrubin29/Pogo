package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Pogo;

public class If extends ConditionalBlock {
	
	private Else elze;

	public If(Block superBlock, String aVal, String bVal, ConditionalBlock.CompareOperation compareOp) {
        super(superBlock, aVal, bVal, compareOp);
	}
	
	public void setElse(Else elze) {
		this.elze = elze;
	}

    public void runAfterParse() throws InvalidCodeException {
    	boolean opSuccess = false;
    	
    	if (compareOp == ConditionalBlock.CompareOperation.EQUALS) {
            if (Pogo.implode(new String[] { aVal }, this).equals(Pogo.implode(new String[] { bVal }, this))) {
                doBlocks();
                opSuccess = true;
            }
        }

        else if (compareOp == ConditionalBlock.CompareOperation.NOTEQUALS) {
            if (!Pogo.implode(new String[] { aVal }, this).equals(Pogo.implode(new String[] { bVal }, this))) {
                doBlocks();
                opSuccess = true;
            }
        }

        else if (compareOp == CompareOperation.GREATERTHAN) {
        	int a, b;
        	
        	try {
                a = Integer.parseInt(Pogo.implode(new String[]{ aVal }, this));
                b = Integer.parseInt(Pogo.implode(new String[] { bVal }, this));
            }
            catch (Exception e) {
                throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers.");
            }
        	
            if (a > b) {
            	doBlocks();
            	opSuccess = true;
            }
        }

        else if (compareOp == CompareOperation.LESSTHAN) {
        	int a, b;
        	
        	try {
                a = Integer.parseInt(Pogo.implode(new String[]{ aVal }, this));
                b = Integer.parseInt(Pogo.implode(new String[] { bVal }, this));
            }
            catch (Exception e) {
                throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers.");
            }
        	
            if (a < b) {
            	doBlocks();
            	opSuccess = true;
            }
        }
    	
    	if (!opSuccess && elze != null) {
    		elze.run();
    		elze.doBlocks();
    	}
    }
}