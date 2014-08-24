package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.Block;
import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.VariableDeclaration;

import java.io.IOException;
import java.io.StreamTokenizer;

public class VariableParser extends Parser<VariableDeclaration> {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("var");
    }

    @Override
    public VariableDeclaration parse(Block superBlock, StreamTokenizer tokenizer) throws IOException, InvalidCodeException {
        // string name = "Noah"

        tokenizer.nextToken(); // Skip the var token.
        tokenizer.nextToken();

        String typeName = tokenizer.sval;

        tokenizer.nextToken();

        String nameName = tokenizer.sval;

        tokenizer.nextToken();

        boolean init = false, newValue = false;

        if (tokenizer.ttype == '=') {
            init = true;

            tokenizer.nextToken();

            if (tokenizer.sval.equals("new")) {
                newValue = true;
            }
        }

        else {
            tokenizer.pushBack(); // If they choose not in initialize it, we want to undo getting the next token since it will be used elsewhere.
        }

        return new VariableDeclaration(superBlock, typeName, nameName, init, newValue, tokenizer);
    }
}