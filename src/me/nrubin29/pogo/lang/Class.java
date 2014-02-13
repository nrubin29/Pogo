package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.cmd.CommandManager;
import me.nrubin29.pogo.gui.GUI;

import java.util.ArrayList;

public class Class extends Block {

	private ArrayList<Method> methods;
	CommandManager commandManager;

    /*
    Given line: method main : Creates block representing method.
        Given line: print Hello : Adds print Hello to last block (method main).
        Given line: if equals Hello Hello : Creates block representing if statement.
            Given line: print duh : Adds print duh to the last block (if statement).
        Given line: end : Ends the if statement. Adds to block before (main method) and removes from temp list.
    Give line: end : Ends the main method.
     */
	public Class(GUI gui, ArrayList<String> code) throws InvalidCodeException {
        super(null);

		this.methods = new ArrayList<Method>();
		this.commandManager = new CommandManager(gui);
		
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
			
			if (line.equals("") || line.equals(" ")) { }

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
		StringBuffer fin = new StringBuffer();
		
		for (String word : str.split(" ")) {
			if (word.startsWith("//")) return fin.toString().trim();
			else fin.append(word + " ");
		}
		
		return fin.toString().trim();
	}
	
	public Method getMethod(String name) throws InvalidCodeException {
		for (Method m : methods) {
			if (m.getName().equals(name)) return m;
		}
		
		throw new InvalidCodeException("Method " + name + " does not exist.");
	}
}