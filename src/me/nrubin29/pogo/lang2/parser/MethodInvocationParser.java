package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;

public class MethodInvocationParser extends Parser<MethodInvocation> {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("invoke");
    }

    @Override
    public MethodInvocation parse(Block superBlock, StreamTokenizer tokenizer) throws IOException, InvalidCodeException {
        // System.print("Hello there")

        tokenizer.nextToken(); // Skip the invoke token.
        tokenizer.nextToken();

        String invokableName = tokenizer.sval;

        tokenizer.nextToken();

        if (tokenizer.ttype != '.') {
            throw new InvalidCodeException("Not a statement.");
        }

        tokenizer.nextToken();

        String methodName = tokenizer.sval;

        tokenizer.nextToken();

        if (tokenizer.ttype != '(') {
            throw new InvalidCodeException("Method invocation does not contain opening parenthesis.");
        }

        ArrayList<Value> params = new ArrayList<>();

        if (tokenizer.ttype != ')') {
            while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
                if (tokenizer.ttype == ')') {
                    break;
                }

                /*
                This only allows for single-word parameters. "Hello, world" would not work.
                 */
                params.add(Utils.handleVariables(tokenizer.sval, tokenizer.ttype, superBlock));
            }
        }

        return new MethodInvocation(superBlock, invokableName, methodName, params);
    }
}