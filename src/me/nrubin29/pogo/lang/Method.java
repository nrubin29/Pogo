package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

import java.util.ArrayList;
import java.util.Arrays;

public class Method extends Block {

	private final String name;
    private final ArrayList<String> collection;
	
	public Method(Block superBlock, String name, ArrayList<String> collection) {
        super(superBlock);

        this.name = name;
        this.collection = new ArrayList<String>(collection);
	}

	public String getName() {
		return name;
	}

    /*
    Idea for handling if statements:
    Have a Line class that extends Block and represents one line. For each line that isn't a block, add it as a Line.
    Once parsing is done, iterate over each Line and run it.
     */
    public void run() throws InvalidCodeException {
        ArrayList<Block> blocks = new ArrayList<Block>();

        boolean collect = false;
        ArrayList<String> localCollection = new ArrayList<String>();
        ConditionalBlock.ConditionalBlockType blockType = null;
        String aVal = null, bVal = null;
        ConditionalBlock.CompareOperation compareOp = null;

        for (String line : collection) {
            boolean blockMatch = false;

            for (ConditionalBlock.ConditionalBlockType bt : ConditionalBlock.ConditionalBlockType.values()) {
                if (line.startsWith(bt.name().toLowerCase())) {
                    blockMatch = true;
                    String[] args = Arrays.copyOfRange(line.split(" "), 1, line.split(" ").length);
                    blockType = bt;
                    aVal = args[1];
                    bVal = args[2];
                    compareOp = ConditionalBlock.CompareOperation.match(args[0]);
                    collect = true;
                }
            }

            if (blockMatch) continue;

            if (line.equals("end") && collect) {
                Block block = null;

                if (blockType == ConditionalBlock.ConditionalBlockType.IF) block = new If(this, aVal, bVal, compareOp, localCollection);
                else if (blockType == ConditionalBlock.ConditionalBlockType.WHILE) block = new While(this, aVal, bVal, compareOp, localCollection);

                blocks.add(block);

                collect = false;
                localCollection.clear();
                blockType = null;
                aVal = null;
                bVal = null;
                compareOp = null;
            }

            else {
                if (collect) localCollection.add(line);
                else blocks.add(new Line(this, line));
            }
        }

        for (Block block : blocks) block.run();
    }
}