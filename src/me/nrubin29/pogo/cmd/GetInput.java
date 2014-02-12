package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.Block;
import me.nrubin29.pogo.GUI;
import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Variable;

public class GetInput extends Command {

	public GetInput() {
		super("getinput");
	}

    /*
    getinput varname
     */
	public void run(GUI gui, Block b, String[] args) throws InvalidCodeException {
        Variable var = b.getVariable(args[0]);

        var.setValue(gui.prompt());
	}
}