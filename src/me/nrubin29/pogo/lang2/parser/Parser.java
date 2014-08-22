package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.Block;
import me.nrubin29.pogo.lang2.InvalidCodeException;

import java.io.IOException;
import java.io.StreamTokenizer;

public abstract class Parser<T extends Block> {

    public abstract boolean shouldParse(String firstToken);

    public abstract T parse(Block superBlock, StreamTokenizer tokenizer) throws IOException, InvalidCodeException;
}