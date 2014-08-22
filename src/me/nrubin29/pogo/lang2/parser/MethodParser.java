package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;

public class MethodParser extends Parser<Method> {

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("method");
    }

    @Override
    public Method parse(Block superBlock, StreamTokenizer tokenizer) throws IOException, InvalidCodeException {
        // public void main

        tokenizer.nextToken(); // Skip the method token.
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

        tokenizer.nextToken();

        /*
        When such a character is encountered by the parser, the parser treats it as a single-character token and sets ttype field to the character value.
         */

        if (tokenizer.ttype != '(') {
            throw new InvalidCodeException("Method declaration missing parentheses.");
        }

        tokenizer.nextToken();

        String beginningParams = tokenizer.sval;

        ArrayList<Parameter> params = new ArrayList<>();

        if (tokenizer.ttype != ')') {
            String[] paramData = new String[]{beginningParams, null};

            while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
                if (paramData[0] == null) { // In this case, we expect this token to be the type.
                    paramData[0] = tokenizer.sval;
                } else { // In this case, we expect this token to be the name.
                    paramData[1] = tokenizer.sval;

                    params.add(new Parameter(Type.match(paramData[0]), paramData[1]));

                    paramData[0] = null;
                    paramData[1] = null;
                }

                if (tokenizer.ttype == ')') {
                    break;
                }
            }
        }

        return new Method(superBlock, methodName, visibility, returnType, params.toArray(new Parameter[params.size()]));
    }
}