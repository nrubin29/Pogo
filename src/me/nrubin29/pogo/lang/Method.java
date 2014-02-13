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

    public void run() throws InvalidCodeException {
        boolean collect = false;
        ArrayList<String> localCollection = new ArrayList<String>();
        Object aVal = null, bVal = null;
        String compareOp = null;

        for (String line : collection) {
            if (line.startsWith("if")) {
                String[] args = Arrays.copyOfRange(line.split(" "), 1, line.split(" ").length);
                aVal = handleVarReferences(this, args[1]);
                bVal = handleVarReferences(this, args[2]);
                compareOp = args[0];
                collect = true;
            }

            else if (line.equals("end") && collect) {
                If i = new If(this, aVal, bVal, compareOp, localCollection);
                i.run();

                collect = false;
                localCollection.clear();
                aVal = null;
                bVal = null;
                compareOp = null;
            }

            else {
                if (collect) localCollection.add(line);
                else ((Class) getBlockTree()[0]).commandManager.parse(this, line);
            }
        }
    }
}