package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import java.io.IOException;

public class WhileParser extends Parser<While> {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("while");
    }

    @Override
    public While parse(Block superBlock, PogoTokenizer tokenizer) throws IOException, InvalidCodeException {
        // (name == "Noah")

        tokenizer.nextToken(); // Skip the while token.

        if (!tokenizer.nextToken().getToken().equals("(")) {
            throw new InvalidCodeException("While statement does not begin with opening parenthesis.");
        }

        Value a = Utils.handleVariables(tokenizer.nextToken(), superBlock);

        Comparison comparison = Comparison.valueOfToken(tokenizer.nextToken().getToken());

        Value b = Utils.handleVariables(tokenizer.nextToken(), superBlock);

        if (!tokenizer.nextToken().getToken().equals(")")) {
            throw new InvalidCodeException("While statement does not end with closing parenthesis.");
        }

        return new While(superBlock, a, b, comparison);
    }
}