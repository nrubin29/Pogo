package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.PogoTokenizer;
import me.nrubin29.pogo.lang2.block.Block;

public abstract class Parser<T extends Block> {

    private Class<T> type;

    public Parser(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    public abstract boolean shouldParseLine(String line);

    public abstract T parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException;
}