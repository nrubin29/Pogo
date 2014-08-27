package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import java.util.ArrayList;

import static me.nrubin29.pogo.lang2.Regex.IDENTIFIER;

public class MethodInvocationParser extends Parser<MethodInvocation> {

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches(IDENTIFIER + "[.]" + IDENTIFIER + "\\((.*,)*?\\)( " + IDENTIFIER + ")?");
    }

    @Override
    public MethodInvocation parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException {
        // System.print("Hello there")

        Token invokableToken = tokenizer.nextToken();

        if (!tokenizer.nextToken().getToken().equals(".")) {
            throw new InvalidCodeException("Not a method invocation.");
        }

        Token methodToken = tokenizer.nextToken();

        if (!tokenizer.nextToken().getToken().equals("(")) {
            throw new InvalidCodeException("Method invocation does not contain opening parenthesis.");
        }

        ArrayList<Token> params = new ArrayList<>();

        while (tokenizer.hasNextToken()) {
            Token token = tokenizer.nextToken();

            if (token.getToken().equals(",")) {
                continue;
            }

            else if (token.getToken().equals(")")) {
                break;
            }

            params.add(token);
        }

        Token optionalVariable = tokenizer.nextToken();

        if (optionalVariable.getType() != Token.TokenType.EMPTY) {
            if (optionalVariable.getType() != Token.TokenType.TOKEN) {
                throw new InvalidCodeException("Capture must be a variable.");
            }
        }

        return new MethodInvocation(superBlock, invokableToken, methodToken, params, optionalVariable);
    }
}