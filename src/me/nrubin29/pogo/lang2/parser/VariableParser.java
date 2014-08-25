package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.Block;
import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.PogoTokenizer;
import me.nrubin29.pogo.lang2.VariableDeclaration;

import java.io.IOException;

public class VariableParser extends Parser<VariableDeclaration> {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("var");
    }

    @Override
    public VariableDeclaration parse(Block superBlock, PogoTokenizer tokenizer) throws IOException, InvalidCodeException {
        // string name = "Noah"

        tokenizer.nextToken(); // Skip the var token.

        String typeName = tokenizer.nextToken().getToken();

        String nameName = tokenizer.nextToken().getToken();

        boolean init = false, newValue = false;

        if (tokenizer.nextToken().getToken().equals("=")) {
            init = true;

            if (tokenizer.nextToken().getToken().equals("new")) {
                newValue = true;
            }

            else {
                tokenizer.pushBack();
            }
        }

        else {
            tokenizer.pushBack(); // If they choose not in initialize it, we want to undo getting the next token since it will be used elsewhere.
        }

        return new VariableDeclaration(superBlock, typeName, nameName, init, newValue, tokenizer.nextToken());
    }
}