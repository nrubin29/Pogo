package me.nrubin29.pogo;

import java.util.ArrayList;

public class Method extends Block {

	private Class c;
	private String name;
	private ArrayList<String> collection;
	
	public Method(Class c, String name, ArrayList<String> collection) {
        super(c);

		this.c = c;
		this.name = name;
		this.collection = new ArrayList<String>(collection);
	}
	
	public String getName() {
		return name;
	}
	
	public void run() throws InvalidCodeException {
		for (String line : collection) {
			c.commandManager.parse(this, line);
		}
	}
}