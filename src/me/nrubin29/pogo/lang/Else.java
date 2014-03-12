package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

public class Else extends Block {

    public Else(Block superBlock) {
        super(superBlock);
    }

    public void runAfterParse() throws InvalidCodeException {
        // We don't want to do anything here.
    }

    @Override
    public String toString() {
        return "Else";
    }
}