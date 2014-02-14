package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.cmd.CommandManager;
import me.nrubin29.pogo.gui.Console;

import java.util.ArrayList;

public class Class extends Block {

    private String[] code;

	private ArrayList<Method> methods;
	CommandManager commandManager;

	public Class(String[] code) {
        super(null);

        this.code = code;
	}

    public void run(Console console) throws InvalidCodeException {
        this.methods = new ArrayList<Method>();
        this.commandManager = new CommandManager(console);

		/*
		 * This list is used when collecting lines for a method, etc.
		 */
        boolean collect = false;
        String blockName = null;
        ArrayList<String> collection = new ArrayList<String>();

		/*
		 * Iterating over each line in the code.
		 */
        for (String line : code) {
            line = trimComments(line);

            if (line.equals("") || line.equals(" "));

            else if (line.startsWith("method ")) {
                collect = true;
                blockName = line.split(" ")[1];
            }

            else if (line.equals("end " + blockName)) {
                methods.add(new Method(this, blockName, collection));

                collect = false;
                blockName = null;
                collection.clear();
            }

            else {
                if (collect) collection.add(line);

                else {
                    /*
                    This is temporary and is implemented for scope purposes.
                     */
                    if (line.startsWith("declare")) commandManager.parse(this, line);
                }
            }
        }

        getMethod("main").run();
    }
	
	private String trimComments(String str) {
		StringBuilder fin = new StringBuilder();
		
		for (String word : str.split(" ")) {
			if (word.startsWith("//")) return fin.toString().trim();
			else fin.append(word).append(" ");
		}
		
		return fin.toString().trim();
	}
	
	public Method getMethod(String name) throws InvalidCodeException {
		for (Method m : methods) {
			if (m.getName().equals(name)) return m;
		}

		throw new InvalidCodeException("Method " + name + " does not exist.");
	}

    public void run() throws InvalidCodeException {
        // No need to do anything here.
    }
}