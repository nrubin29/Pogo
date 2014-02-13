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
        boolean collect = false, consume = false;
        ArrayList<String> localCollection = new ArrayList<String>();

        for (String line : collection) {
            if (line.startsWith("if")) {
                String[] args = Arrays.copyOfRange(line.split(" "), 1, line.split(" ").length);
                String aVal = handleVarReferences(this, args[1]);
                String bVal = handleVarReferences(this, args[2]);
                if (args[0].equals("equals")) {
                    if (aVal.equals(bVal)) collect = true;
                    else consume = true;
                }
            }

            else if (line.equals("end") && collect) {
                for (String str : localCollection) {
                    ((Class) getBlockTree()[0]).commandManager.parse(this, str);
                }

                collect = false;
                localCollection.clear();
            }

            else {
                if (consume) continue;
                if (collect) localCollection.add(line);
                else ((Class) getBlockTree()[0]).commandManager.parse(this, line);
            }
        }
    }
}