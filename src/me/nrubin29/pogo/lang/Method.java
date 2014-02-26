package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

public class Method extends Block {

	private final String name;

	public Method(Block superBlock, String name) {
        super(superBlock);

        this.name = name;
	}

	public String getName() {
		return name;
	}

    public void runAfterParse() throws InvalidCodeException {
    	doBlocks();
    }
}