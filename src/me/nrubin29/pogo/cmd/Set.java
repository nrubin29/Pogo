package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.Block;
import me.nrubin29.pogo.GUI;
import me.nrubin29.pogo.InvalidCodeException;

import java.util.Arrays;

public class Set extends Command {

	public Set() {
		super("set");
	}
	
	/*
	 * set str Hello
	 */
	public void run(GUI gui, Block b, String[] args) throws InvalidCodeException {
		String newVal = "";

        for (String str : Arrays.copyOfRange(args, 1, args.length)) {
            newVal += str + " ";
        }

        b.getVariable(args[0]).setValue(newVal);
	}
}