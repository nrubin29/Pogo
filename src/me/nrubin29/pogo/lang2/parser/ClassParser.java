package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.Block;
import me.nrubin29.pogo.lang2.Class;
import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.PogoTokenizer;

public class ClassParser extends Parser<Class> {

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches("^(class [a-zA-Z]([a-zA-Z0-9]*)?)");
    }

    @Override
    public Class parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException {
        tokenizer.nextToken(); // Skip the class token.
        return new Class(tokenizer.nextToken().getToken());
    }
}