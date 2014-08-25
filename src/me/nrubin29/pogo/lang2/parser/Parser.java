package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.Block;
import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.PogoTokenizer;

import java.io.IOException;

public abstract class Parser<T extends Block> {

    public abstract boolean shouldParse(String firstToken);

    public abstract T parse(Block superBlock, PogoTokenizer tokenizer) throws IOException, InvalidCodeException;
}