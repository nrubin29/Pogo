package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import java.io.IOException;
import java.io.StreamTokenizer;

public class MethodParser extends Parser {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("method");
    }

    @Override
    public Block parse(Block superBlock, StreamTokenizer tokenizer) throws IOException, InvalidCodeException {
        // public void main

        tokenizer.nextToken();

        Visibility visibility;

        try {
            visibility = Visibility.valueOf(tokenizer.sval.toUpperCase());
        } catch (Exception e) {
            throw new InvalidCodeException("Expected visibility, got " + tokenizer.sval, e);
        }

        tokenizer.nextToken();

        Type returnType = null;

        try {
            returnType = PrimitiveType.valueOf(tokenizer.sval.toUpperCase());
        } catch (Exception ignored) {

        }

        if (returnType == null) {
            returnType = IDEInstance.CURRENT_INSTANCE.getPogoClass(tokenizer.sval);
        }

        if (returnType == null) {
            throw new InvalidCodeException("Expected return type, got " + tokenizer.sval, new NullPointerException());
        }

        tokenizer.nextToken();

        String methodName = tokenizer.sval;

        // TODO: Parse parameters.

        return new Method(superBlock, methodName, visibility, returnType);
    }
}