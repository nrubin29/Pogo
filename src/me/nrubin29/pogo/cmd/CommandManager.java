package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.gui.Console;
import me.nrubin29.pogo.lang.Block;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager {

	private ArrayList<Command> cmds = new ArrayList<Command>();
	
	private Console console;
	
	public CommandManager(Console console) {
		this.console = console;

        cmds.add(new Declare());
        cmds.add(new GetInput());
        cmds.add(new Invoke());
		cmds.add(new Print());
        cmds.add(new Set());
	}
	
	public void parse(Block b, String input) throws InvalidCodeException {
        String[] all = input.split(" ");
        String cmd = all[0];
        String[] args = Arrays.copyOfRange(all, 1, all.length);

        Command c = null;
        
        for (Command cm : cmds) {
        	if (cm.getName().equals(cmd)) c = cm;
        }
        
        if (c == null) throw new InvalidCodeException("Invalid operation call.");

        else c.run(console, b, args);
    }
}