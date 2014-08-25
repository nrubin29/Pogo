package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import java.io.IOException;
import java.util.ArrayList;

public class MethodInvocationParser extends Parser<MethodInvocation> {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("invoke");
    }

    @Override
    public MethodInvocation parse(Block superBlock, PogoTokenizer tokenizer) throws IOException, InvalidCodeException {
        // System.print("Hello there")

        tokenizer.nextToken(); // Skip the invoke token.

        String invokableName = tokenizer.nextToken().getToken();

        if (!tokenizer.nextToken().getToken().equals(".")) {
            throw new InvalidCodeException("Not a method invocation.");
        }

        String methodName = tokenizer.nextToken().getToken();

        if (!tokenizer.nextToken().getToken().equals("(")) {
            throw new InvalidCodeException("Method invocation does not contain opening parenthesis.");
        }

        ArrayList<Token> params = new ArrayList<>();

        while (tokenizer.hasNextToken()) {
            Token nextToken = tokenizer.nextToken();

            if (nextToken.getToken().equals(")")) {
                break;
            }

            params.add(nextToken);
        }

        Token optionalVariable = tokenizer.nextToken();

        if (optionalVariable.getType() != Token.TokenType.EMPTY) {
            if (optionalVariable.getType() != Token.TokenType.TOKEN) {
                throw new InvalidCodeException("Capture must be a variable.");
            }
        }

        return new MethodInvocation(superBlock, invokableName, methodName, params, optionalVariable);
    }
}