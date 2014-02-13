package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.gui.GUI;
import me.nrubin29.pogo.lang.Block;

public class GetInput extends Command {

	public GetInput() {
		super("getinput");
	}

    /*
    getinput varname
     */
	public void run(GUI gui, Block b, String[] args) throws InvalidCodeException {
        b.getVariable(args[0]).setValue(gui.prompt());
	}
}