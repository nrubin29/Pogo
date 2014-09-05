package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.PogoTokenizer;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.block.Return;

public class ReturnParser extends Parser<Return> {

    public ReturnParser() {
        super(Return.class);
    }

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches("return .*");
    }

    @Override
    public Return parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException {
        // "Noah"

        tokenizer.nextToken(); // Skip the return token.
        return new Return(superBlock, tokenizer.nextToken());
    }
}