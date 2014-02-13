package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.gui.GUI;
import me.nrubin29.pogo.lang.Block;

import java.util.Arrays;

public class Set extends Command {

	public Set() {
		super("set");
	}
	
	/*
	 * set str Hello
	 */
	public void run(GUI gui, Block b, String[] args) throws InvalidCodeException {
		StringBuffer newVal = new StringBuffer();

        for (String str : Arrays.copyOfRange(args, 1, args.length)) {
            newVal.append(str + " ");
        }

        b.getVariable(args[0]).setValue(newVal.toString());
	}
}