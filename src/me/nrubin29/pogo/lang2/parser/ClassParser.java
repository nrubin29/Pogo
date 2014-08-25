package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.Block;
import me.nrubin29.pogo.lang2.Class;
import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.PogoTokenizer;

import java.io.IOException;

public class ClassParser extends Parser<Class> {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("class");
    }

    @Override
    public Class parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException, IOException {
        tokenizer.nextToken(); // Skip the class token.
        return new Class(tokenizer.nextToken().getToken());
    }
}