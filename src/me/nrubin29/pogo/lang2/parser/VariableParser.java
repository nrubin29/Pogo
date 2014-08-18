package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;
import me.nrubin29.pogo.lang2.Class;

import java.io.IOException;
import java.io.StreamTokenizer;

public class VariableParser extends Parser {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("var");
    }

    @Override
    public Block parse(Block superBlock, StreamTokenizer tokenizer) throws IOException, InvalidCodeException {
        // string name = "Noah"

        tokenizer.nextToken();

        Type type = Type.match(tokenizer.sval);

        tokenizer.nextToken();

        String name = tokenizer.sval;

        Variable variable = new Variable(superBlock, name, type);

        tokenizer.nextToken();

        if (tokenizer.sval.equals("=")) {
            tokenizer.nextToken();

            if (tokenizer.sval.equals("new")) {
                if (type instanceof PrimitiveType) {
                    throw new InvalidCodeException("Attempted to instantiate primitive type with new.");
                } else {
                    variable.setValue(((Class) type).clone());
                }
            }

            variable.setValue(Utils.handleVariables(tokenizer.sval, superBlock)); // The value cannot have any spaces in this case.
        } else {
            tokenizer.pushBack(); // If they choose not in initialize it, we want to undo getting the next token since it will be used elsewhere.
        }

        superBlock.addVariable(variable);
        return null;
    }
}