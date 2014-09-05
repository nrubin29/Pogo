package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.block.ConditionalBlock;
import me.nrubin29.pogo.lang2.block.DoWhile;
import me.nrubin29.pogo.lang2.block.While;

import static me.nrubin29.pogo.lang2.Regex.COMPARISON;
import static me.nrubin29.pogo.lang2.Regex.IDENTIFIER_OR_LITERAL;

public class WhileParser extends Parser<ConditionalBlock> {

    public WhileParser() {
        super(ConditionalBlock.class);
    }

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches("(while|dowhile) \\(" + IDENTIFIER_OR_LITERAL + " " + COMPARISON + " " + IDENTIFIER_OR_LITERAL + "\\)");
    }

    @Override
    public ConditionalBlock parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException {
        // while|dowhile (name == "Noah")

        Token type = tokenizer.nextToken();

        if (!tokenizer.nextToken().getToken().equals("(")) {
            throw new InvalidCodeException("While statement does not begin with opening parenthesis.");
        }

        Value a = Utils.parseToken(tokenizer.nextToken(), superBlock);

        Comparison comparison = Comparison.valueOfToken(tokenizer.nextToken().getToken());

        Value b = Utils.parseToken(tokenizer.nextToken(), superBlock);

        if (!tokenizer.nextToken().getToken().equals(")")) {
            throw new InvalidCodeException("While statement does not end with closing parenthesis.");
        }

        if (type.getToken().equals("while")) {
            return new While(superBlock, a, b, comparison);
        }

        else {
            return new DoWhile(superBlock, a, b, comparison);
        }
    }
}