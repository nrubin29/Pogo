package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.PogoTokenizer;
import me.nrubin29.pogo.lang2.Token;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.block.VariableReassignment;

import static me.nrubin29.pogo.lang2.Regex.IDENTIFIER;

public class VariableReassignmentParser extends Parser<VariableReassignment> {

    public VariableReassignmentParser() {
        super(VariableReassignment.class);
    }

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches(IDENTIFIER + "( )?=( )?.*");
    }

    @Override
    public VariableReassignment parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException {
        // name = "Noah"
        // person = new("Noah")

        Token name = tokenizer.nextToken();

        return new VariableReassignment(superBlock, name, tokenizer);
    }
}