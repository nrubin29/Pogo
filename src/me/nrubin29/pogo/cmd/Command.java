package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.Block;
import me.nrubin29.pogo.GUI;
import me.nrubin29.pogo.InvalidCodeException;

public abstract class Command {

	private String name;
	
	public Command(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract void run(GUI gui, Block b, String[] args) throws InvalidCodeException;
}