package me.nrubin29.pogo;

import java.util.ArrayList;

import me.nrubin29.pogo.Variable.VariableType;
import me.nrubin29.pogo.cmd.CommandManager;

public class Class {

	private ArrayList<Variable> vars;
	private ArrayList<Method> methods;
	
	CommandManager commandManager;
	
	public Class(GUI gui, String code) throws InvalidCodeException {
		this.vars = new ArrayList<Variable>();
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
		for (String line : code.split("\n")) {
			line = trimComments(line);
			
			if (line.equals("") || line.equals(" ")) continue;
			
			else if (line.startsWith("method ")) {
				collect = true;
				blockName = line.split(" ")[1];
			}
			
			else if (line.equals("end") && collect) {
				collect = false;
				
				/*
				 * NOTE: This assumes that methods are the only blocks.
				 */
				
				methods.add(new Method(this, blockName, collection));
				
				collection.clear();
			}
			
			else {
				if (collect) collection.add(line);
			}
		}
		
		getMethod("main").run();
	}
	
	private String trimComments(String str) {
		String fin = "";
		
		for (String word : str.split(" ")) {
			if (word.startsWith("//")) return fin.trim();
			else fin += word + " ";
		}
		
		return fin.trim();
	}
	
	public Method getMethod(String name) throws InvalidCodeException {
		for (Method m : methods) {
			if (m.getName().equals(name)) return m;
		}
		
		throw new InvalidCodeException("Method " + name + " does not exist.");
	}
	
	public void addVariable(VariableType t, String name, Object value) {
		vars.add(new Variable(t, name, value));
	}
	
	public Variable getVariable(String name) throws InvalidCodeException {
		for (Variable v : vars) {
			if (v.getName().equals(name)) return v;
		}
		
		throw new InvalidCodeException("Variable " + name + " is not declared.");
	}
}