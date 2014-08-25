package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import java.io.IOException;
import java.util.ArrayList;

public class MethodParser extends Parser<Method> {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("method");
    }

    @Override
    public Method parse(Block superBlock, PogoTokenizer tokenizer) throws IOException, InvalidCodeException {
        // public void main

        tokenizer.nextToken(); // Skip the method token.

        Token visToken = tokenizer.nextToken();
        Visibility visibility;

        try {
            visibility = Visibility.valueOf(visToken.getToken().toUpperCase());
        } catch (Exception e) {
            throw new InvalidCodeException("Expected visibility, got " + visToken, e);
        }

        Type returnType = Type.match(tokenizer.nextToken().getToken());

        String methodName = tokenizer.nextToken().getToken();

        if (!tokenizer.nextToken().getToken().equals("(")) {
            throw new InvalidCodeException("Method declaration missing parentheses.");
        }

        Token beginningParams = tokenizer.nextToken();

        ArrayList<Parameter> params = new ArrayList<>();

        if (!beginningParams.getToken().equals(")")) {
            String[] paramData = new String[] { beginningParams.getToken(), null };

            while (tokenizer.hasNextToken()) {
                Token token = tokenizer.nextToken();

                if (paramData[0] == null) { // In this case, we expect this token to be the type.
                    paramData[0] = token.getToken();
                } else { // In this case, we expect this token to be the name.
                    paramData[1] = token.getToken();

                    params.add(new Parameter(Type.match(paramData[0]), paramData[1]));

                    paramData[0] = null;
                    paramData[1] = null;
                }

                if (token.getToken().equals(")")) {
                    break;
                }
            }
        }

        return new Method(superBlock, methodName, visibility, returnType, params.toArray(new Parameter[params.size()]));
    }
}