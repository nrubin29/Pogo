package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import java.util.ArrayList;

import static me.nrubin29.pogo.lang2.Regex.IDENTIFIER;
import static me.nrubin29.pogo.lang2.Regex.VISIBILITY;

public class MethodParser extends Parser<Method> {

    public MethodParser() {
        super(Method.class);
    }

    @Override
    public boolean shouldParseLine(String line) {
        // TODO: method public void stuff(,string str) is valid. Need to fix validity of leading comma.
        return line.matches("method " + VISIBILITY + " " + IDENTIFIER + " " + IDENTIFIER + "( )?\\((" + IDENTIFIER + " " + IDENTIFIER + ")?((,( )?" + IDENTIFIER + " " + IDENTIFIER + ")?)*\\)?");
    }

    @Override
    public Method parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException {
        // public void main

        tokenizer.nextToken(); // Skip the method token.

        Token visToken = tokenizer.nextToken();
        Visibility visibility;

        try {
            visibility = Visibility.valueOf(visToken.getToken().toUpperCase());
        } catch (Exception e) {
            throw new InvalidCodeException("Expected visibility, got " + visToken, e);
        }

        Token returnToken = tokenizer.nextToken();

        String methodName = tokenizer.nextToken().getToken();

        if (!tokenizer.nextToken().getToken().equals("(")) {
            throw new InvalidCodeException("Method declaration missing parentheses.");
        }

        Token beginningParams = tokenizer.nextToken();

        ArrayList<Parameter> params = new ArrayList<>();

        if (!beginningParams.getToken().equals(")")) {
            Object[] paramData = { beginningParams, null };

            while (tokenizer.hasNextToken()) {
                Token token = tokenizer.nextToken();

                if (paramData[0] == null) { // In this case, we expect this token to be the type.
                    paramData[0] = token;
                }

                else { // In this case, we expect this token to be the name.
                    paramData[1] = token.getToken();

                    params.add(new Parameter((Token) paramData[0], (String) paramData[1]));

                    paramData[0] = null;
                    paramData[1] = null;
                }

                if (token.getToken().equals(")")) {
                    break;
                }
            }
        }

        return new Method(superBlock, methodName, visibility, returnToken, params.toArray(new Parameter[params.size()]));
    }
}