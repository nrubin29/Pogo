package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import java.io.IOException;

public class ForParser extends Parser<For> {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("for");
    }

    @Override
    public For parse(Block superBlock, PogoTokenizer tokenizer) throws IOException, InvalidCodeException {
        // (1 10)

        tokenizer.nextToken(); // Skip the for token.

        if (!tokenizer.nextToken().getToken().equals("(")) {
            throw new InvalidCodeException("For statement does not begin with opening parenthesis.");
        }

        Token lower = tokenizer.nextToken(), upper = tokenizer.nextToken();

        if (!tokenizer.nextToken().getToken().equals(")")) {
            throw new InvalidCodeException("For statement does not end with closing parenthesis.");
        }

        return new For(superBlock, lower, upper);
    }
}