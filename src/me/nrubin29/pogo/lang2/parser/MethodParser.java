package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;

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

        if (!tokenizer.sval.contains("(")) {
            throw new InvalidCodeException("Method declaration missing parentheses.");
        }

        String methodName = tokenizer.sval.substring(0, tokenizer.sval.indexOf("("));

        String beginningParams = tokenizer.sval.substring(tokenizer.sval.indexOf("(") + 1);

        ArrayList<Parameter> params = new ArrayList<>();

        if (!beginningParams.equals(")")) {
            String[] paramData = new String[]{beginningParams, null};

            int code;
            while ((code = tokenizer.nextToken()) == StreamTokenizer.TT_WORD || code == StreamTokenizer.TT_NUMBER) {
                if (paramData[0] == null) { // In this case, we expect this token to be the type.
                    paramData[0] = tokenizer.sval;
                } else { // In this case, we expect this token to be the name.
                    paramData[1] = tokenizer.sval;

                    if (paramData[1].endsWith(",")) { // This is starting to get a bit hard-coded-ey.
                        paramData[1] = paramData[1].substring(0, paramData[1].length() - 1);
                    }

                    if (paramData[1].endsWith(")")) {
                        paramData[1] = paramData[1].substring(0, paramData[1].length() - 1);
                    }

                    params.add(new Parameter(Type.match(paramData[0]), paramData[1]));

                    paramData[0] = null;
                    paramData[1] = null;
                }

                if (tokenizer.sval.endsWith(")")) {
                    break;
                }
            }
        }

        return new Method(superBlock, methodName, visibility, returnType, params.toArray(new Parameter[params.size()]));
    }
}