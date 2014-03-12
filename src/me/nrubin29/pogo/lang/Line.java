package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

public class Line extends Block {

    private final String line;

    public Line(Block superBlock, String line) {
        super(superBlock);

        this.line = line;
    }

    public void runAfterParse() throws InvalidCodeException {
        ((Class) getBlockTree()[0]).functionManager.parse(getSuperBlock(), line);
    }

    @Override
    public String toString() {
        return "Line text=" + line;
    }
}