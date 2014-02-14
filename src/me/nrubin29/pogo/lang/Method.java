package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

import java.util.ArrayList;
import java.util.Arrays;

public class Method extends Block {

	private String name;
    private ArrayList<String> collection;
	
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
        String aVal = null, bVal = null;
        If.CompareOperation compareOp = null;

        for (String line : collection) {
            if (line.startsWith("if")) {
                String[] args = Arrays.copyOfRange(line.split(" "), 1, line.split(" ").length);
                aVal = args[1];
                bVal = args[2];
                compareOp = If.CompareOperation.match(args[0]);
                collect = true;
            }

            else if (line.equals("end") && collect) {
                If i = new If(this, aVal, bVal, compareOp, localCollection);
                blocks.add(i);

                collect = false;
                localCollection.clear();
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