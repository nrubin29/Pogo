package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.Block;
import me.nrubin29.pogo.lang2.Class;

import java.io.IOException;
import java.io.StreamTokenizer;

public class ClassParser extends Parser {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("class");
    }

    @Override
    public Block parse(Block superBlock, StreamTokenizer tokenizer) throws IOException {
        tokenizer.nextToken();
        return new Class(superBlock, tokenizer.sval);
    }
}