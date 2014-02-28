package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.function.FunctionManager;
import me.nrubin29.pogo.gui.Console;
import me.nrubin29.pogo.lang.Variable.VariableType;

import java.util.ArrayList;

public class Class extends Block {

    private final String[] code;

	private ArrayList<Method> methods;
	FunctionManager functionManager;

	public Class(String[] code) {
        super(null);

        this.code = code;
	}

    public void run(Console console) throws InvalidCodeException {
        this.methods = new ArrayList<Method>();
        this.functionManager = new FunctionManager(console);

        Method currentMethod = null;
        
        for (String line : code) {
            line = trimComments(line);

            if (line.startsWith("method ")) {
            	String[] mArgs = line.split(" ")[1].split(":");
            	if (mArgs.length == 1) throw new InvalidCodeException("Did not specify return type for method " + mArgs[0] + "."); 
                currentMethod = new Method(this, mArgs[0], VariableType.match(mArgs[1]));
            }

            else if (currentMethod != null && line.equals("end " + currentMethod.getName())) {
                methods.add(currentMethod);

                currentMethod = null;
            }

            else if (line.startsWith("declare")) functionManager.parse(this, line);

            else {
                if (currentMethod != null && !line.equals("") && !line.equals(" ")) currentMethod.addLine(line);
            }
        }

        Method main = getMethod("main");
        main.run();
        main.invoke();

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