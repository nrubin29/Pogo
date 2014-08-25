package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.Block;
import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.PogoTokenizer;
import me.nrubin29.pogo.lang2.Return;

import java.io.IOException;

public class ReturnParser extends Parser<Return> {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("return");
    }

    @Override
    public Return parse(Block superBlock, PogoTokenizer tokenizer) throws IOException, InvalidCodeException {
        // "Noah"

        tokenizer.nextToken(); // Skip the return token.
        return new Return(superBlock, tokenizer.nextToken());
    }
}