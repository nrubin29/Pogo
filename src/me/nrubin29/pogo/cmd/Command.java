package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.Class;
import me.nrubin29.pogo.GUI;
import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Method;

public abstract class Command {

	private String name;
	
	public Command(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract void run(GUI gui, Class c, Method m, String[] args) throws InvalidCodeException;
}