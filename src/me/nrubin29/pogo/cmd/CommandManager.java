package me.nrubin29.pogo.cmd;

import java.util.ArrayList;
import java.util.Arrays;

import me.nrubin29.pogo.Class;
import me.nrubin29.pogo.GUI;
import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Method;

public class CommandManager {

	private ArrayList<Command> cmds = new ArrayList<Command>();
	
	private GUI gui;
	
	public CommandManager(GUI gui) {
		this.gui = gui;
		
		cmds.add(new Print());
		cmds.add(new Declare());
	}
	
	public void parse(Class clazz, Method m, String input) throws InvalidCodeException {
        String[] all = input.split(" ");
        String cmd = all[0];
        String[] args = Arrays.copyOfRange(all, 1, all.length);

        Command c = null;
        
        for (Command cm : cmds) {
        	if (cm.getName().equals(cmd)) c = cm;
        }
        
        if (c == null) throw new InvalidCodeException("Invalid operation call.");

        else c.run(gui, clazz, m, args);
    }
}