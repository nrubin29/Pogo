package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public abstract class Block {

    private final Block superBlock;
    private final ArrayList<Variable> vars;
    private final ArrayList<Block> subBlocks;
    private final ArrayList<String> lines;

    Block(Block superBlock) {
        this.superBlock = superBlock;
        this.vars = new ArrayList<Variable>();
        this.subBlocks = new ArrayList<Block>();
        this.lines = new ArrayList<String>();
    }

    void addLine(String line) {
        lines.add(line);
    }

    Block getSuperBlock() {
        return superBlock;
    }

    public Block[] getBlockTree() {
        ArrayList<Block> tree = new ArrayList<Block>();

        Block b = this;

        while (b != null) {
            tree.add(b);
            b = b.getSuperBlock();
        }

        Collections.reverse(tree);

        return tree.toArray(new Block[tree.size()]);
    }

    public void addVariable(Variable.VariableType t, String name, Object value) {
        vars.add(new Variable(t, name, value));
    }

    public Variable getVariable(String name) throws InvalidCodeException {
        for (Block b : Arrays.copyOfRange(getBlockTree(), 0, getBlockTree().length - 1)) {
            if (b.hasVariable(name)) return b.getVariable(name);
        }

        for (Variable v : vars) {
            if (v.getName().equals(name)) return v;
        }

        throw new InvalidCodeException("Variable " + name + " is not declared.");
    }

    boolean hasVariable(String name) {
        for (Variable v : vars) {
            if (v.getName().equals(name)) return true;
        }

        return false;
    }
    
    public void run() throws InvalidCodeException {
    	subBlocks.clear();
    	
    	If lastIf = null;
    	
        Block currentBlock = null;
        int numEndsIgnore = 0;

        lineLoop: for (String line : lines) {
        	for (ConditionalBlock.ConditionalBlockType bt : ConditionalBlock.ConditionalBlockType.values()) {
                if (line.startsWith(bt.name().toLowerCase())) {
                	if (currentBlock == null) {
                    	String[] args = Arrays.copyOfRange(line.split(" "), 1, line.split(" ").length);

                        if (bt == ConditionalBlock.ConditionalBlockType.IF) {
                        	currentBlock = new If(this, args[1], args[2], ConditionalBlock.CompareOperation.match(args[0]));
                        }
                        
                        else if (bt == ConditionalBlock.ConditionalBlockType.ELSE) {
                        	if (lastIf == null) throw new InvalidCodeException("Else without if.");
                        	
                        	currentBlock = new Else(this);
                        }
                        
                        else if (bt == ConditionalBlock.ConditionalBlockType.WHILE) {
                        	currentBlock = new While(this, args[1], args[2], ConditionalBlock.CompareOperation.match(args[0]));
                        }
                    }
                    
                    else {
                    	currentBlock.addLine(line);
                    	numEndsIgnore++;
                    }
                    
                    continue lineLoop;
                }
            }

            if (line.equals("end")) {
            	/*
            	If we are supposed to ignore this "end"
            	 */
            	if (numEndsIgnore > 0) {
            		numEndsIgnore--;
            		currentBlock.addLine("end");
            		continue;
            	}
            	
                /*
                If the end pertains to a nested statement...
                 */
                if (currentBlock != null) {
                    currentBlock.addLine("end");
                    if (!(currentBlock instanceof Else)) subBlocks.add(currentBlock);
                    
                    if (currentBlock instanceof If) {
                    	lastIf = (If) currentBlock;
                    }
                    
                    else if (currentBlock instanceof Else) {
                    	lastIf.setElse((Else) currentBlock);
                    	lastIf = null;
                    }
                    
                    currentBlock = null;
                }

                /*
                If the end pertains to this statement...
                 */
                else break;
            }

            else {
                if (currentBlock != null) currentBlock.addLine(line);
                else subBlocks.add(new Line(this, line));
            }
        }

        runAfterParse();
    }

    protected abstract void runAfterParse() throws InvalidCodeException;

    final void doBlocks() throws InvalidCodeException {
        for (Block block : subBlocks) {
            block.run();
        }
    }
}