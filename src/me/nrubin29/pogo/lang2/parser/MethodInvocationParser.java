package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.Block;
import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.MethodInvocation;

import java.io.IOException;
import java.io.StreamTokenizer;

public class MethodInvocationParser extends Parser<MethodInvocation> {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("invoke");
    }

    @Override
    public MethodInvocation parse(Block superBlock, StreamTokenizer tokenizer) throws IOException, InvalidCodeException {
        // System.print()

        tokenizer.nextToken(); // Skip the invoke token.
        tokenizer.nextToken();

        String invokableName = tokenizer.sval;

        tokenizer.nextToken();

        if (tokenizer.ttype != '.') {
            throw new InvalidCodeException("Not a statement.");
        }

        tokenizer.nextToken();

        String methodName = tokenizer.sval;

        return new MethodInvocation(superBlock, invokableName, methodName);
    }
}