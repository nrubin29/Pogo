package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.cmd.CommandManager;
import me.nrubin29.pogo.gui.Console;

import java.util.ArrayList;

public class Class extends Block {

    private final String[] code;

	private ArrayList<Method> methods;
	CommandManager commandManager;

	public Class(String[] code) {
        super(null);

        this.code = code;
	}

    public void run(Console console) throws InvalidCodeException {
        this.methods = new ArrayList<Method>();
        this.commandManager = new CommandManager(console);

        Method currentMethod = null;

		/*
		 * Iterating over each line in the code.
		 */
        for (String line : code) {
            line = trimComments(line);

            if (line.startsWith("method ")) {
                currentMethod = new Method(this, line.split(" ")[1]);
            }

            else if (currentMethod != null && line.equals("end " + currentMethod.getName())) {
                methods.add(currentMethod);

                currentMethod = null;
            }

            else if (line.startsWith("declare")) commandManager.parse(this, line);

            else {
                if (currentMethod != null && !line.equals("") && !line.equals(" ")) currentMethod.addLine(line);
            }
        }

        getMethod("main").run();

        console.write("--Terminated.");
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

    public void runAfterParse() throws InvalidCodeException {
        // No need to do anything here.
    }
}