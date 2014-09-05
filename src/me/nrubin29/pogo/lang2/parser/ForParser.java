package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.PogoTokenizer;
import me.nrubin29.pogo.lang2.Token;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.block.For;

import static me.nrubin29.pogo.lang2.Regex.IDENTIFIER_OR_LITERAL;

public class ForParser extends Parser<For> {

    public ForParser() {
        super(For.class);
    }

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches("for \\(" + IDENTIFIER_OR_LITERAL + " " + IDENTIFIER_OR_LITERAL + "\\)");
    }

    @Override
    public For parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException {
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