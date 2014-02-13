package me.nrubin29.pogo.cmd;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.gui.GUI;
import me.nrubin29.pogo.lang.Block;
import me.nrubin29.pogo.lang.Variable;

public class GetInput extends Command {

	public GetInput() {
		super("getinput");
	}

    /*
    getinput varname
     */
	public void run(GUI gui, Block b, String[] args) throws InvalidCodeException {
        Variable v = b.getVariable(args[0]);
        String in = gui.prompt();

        v.getType().validateValue(in);
        v.setValue(in);
	}
}