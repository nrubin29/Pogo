package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.block.Class;
import me.nrubin29.pogo.lang2.tokenizer.Tokenizer;

import static me.nrubin29.pogo.lang2.tokenizer.Regex.IDENTIFIER;

public class ClassParser extends Parser<Class> {

    public ClassParser() {
        super(Class.class);
    }

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches("class " + IDENTIFIER);
    }

    @Override
    public Class parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
        tokenizer.nextToken(); // Skip the class token.

        return new Class(tokenizer.nextToken().getToken());
    }
}