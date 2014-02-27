package me.nrubin29.pogo.function;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.gui.Console;
import me.nrubin29.pogo.lang.Block;

import java.util.ArrayList;
import java.util.Arrays;

public class FunctionManager {

	private final ArrayList<Function> cmds = new ArrayList<Function>();
	
	private final Console console;
	
	public FunctionManager(Console console) {
		this.console = console;

        cmds.add(new Declare());
        cmds.add(new GetInput());
        cmds.add(new Invoke());
        cmds.add(new Math());
		cmds.add(new Print());
        cmds.add(new Random());
        cmds.add(new Set());
	}
	
	public void parse(Block b, String input) throws InvalidCodeException {
        String[] all = input.split(" ");
        String cmd = all[0];
        String[] args = Arrays.copyOfRange(all, 1, all.length);

        Function c = null;
        
        for (Function cm : cmds) {
        	if (cm.getName().equals(cmd)) c = cm;
        }
        
        if (c == null) throw new InvalidCodeException("Function " + cmd + " does not exist.");

        else c.run(console, b, args);
    }
}