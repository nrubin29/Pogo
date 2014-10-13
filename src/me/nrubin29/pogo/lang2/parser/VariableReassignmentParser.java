package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.block.VariableReassignment;
import me.nrubin29.pogo.lang2.expression.Expression;
import me.nrubin29.pogo.lang2.tokenizer.Token;
import me.nrubin29.pogo.lang2.tokenizer.Tokenizer;

import static me.nrubin29.pogo.lang2.tokenizer.Regex.IDENTIFIER;

public class VariableReassignmentParser extends Parser<VariableReassignment> {

    public VariableReassignmentParser() {
        super(VariableReassignment.class);
    }

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches(IDENTIFIER + "( )?=( )?.*");
    }

    @Override
    public VariableReassignment parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
        // name = "Noah"
        // person = new("Noah")

        Token name = tokenizer.nextToken();

        Token possibleEquals = tokenizer.nextToken();

        if (possibleEquals.getToken() == null || !possibleEquals.getToken().equals("=")) {
            tokenizer.pushBack();
        }

        return new VariableReassignment(superBlock, name, Expression.parse(tokenizer, superBlock, null));
    }
}