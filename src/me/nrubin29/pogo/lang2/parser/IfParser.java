package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import java.io.IOException;
import java.io.StreamTokenizer;

public class IfParser extends Parser {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("if");
    }

    @Override
    public Block parse(Block superBlock, StreamTokenizer tokenizer) throws IOException, InvalidCodeException {
        // (name == "Noah")

        tokenizer.nextToken(); // Skip the if token.
        tokenizer.nextToken();

        if (tokenizer.ttype != '(') {
            throw new InvalidCodeException("If statement does not begin with opening parenthesis.");
        }

        tokenizer.nextToken();

        Value a = Utils.handleVariables(tokenizer.sval, tokenizer.ttype, superBlock);

        tokenizer.nextToken();

        char firstComparisonToken = (char) tokenizer.ttype;

        tokenizer.nextToken();

        char secondComparisonToken = (char) tokenizer.ttype;

        Comparison comparison = Comparison.valueOfToken(firstComparisonToken + "" + secondComparisonToken);

        tokenizer.nextToken();

        Value b = Utils.handleVariables(tokenizer.sval, tokenizer.ttype, superBlock);

        tokenizer.nextToken();

        if (tokenizer.ttype != ')') {
            throw new InvalidCodeException("If statement does not end with closing parenthesis.");
        }

        return new If(superBlock, ConditionalBlockType.IF, a, b, comparison);
    }
}