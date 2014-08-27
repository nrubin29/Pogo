package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import java.util.ArrayList;

import static me.nrubin29.pogo.lang2.Regex.IDENTIFIER;
import static me.nrubin29.pogo.lang2.Regex.VISIBILITY;

public class ConstructorParser extends Parser<Constructor> {

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches("constructor " + VISIBILITY + "( )?\\((" + IDENTIFIER + " " + IDENTIFIER + ",( )?)*\\)?");
    }

    @Override
    public Constructor parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException {
        // public (string name)

        tokenizer.nextToken(); // Skip the constructor token.

        Token visToken = tokenizer.nextToken();
        Visibility visibility;

        try {
            visibility = Visibility.valueOf(visToken.getToken().toUpperCase());
        } catch (Exception e) {
            throw new InvalidCodeException("Expected visibility, got " + visToken, e);
        }

        if (!tokenizer.nextToken().getToken().equals("(")) {
            throw new InvalidCodeException("Method declaration missing parentheses.");
        }

        Token beginningParams = tokenizer.nextToken();

        ArrayList<Parameter> params = new ArrayList<>();

        if (!beginningParams.getToken().equals(")")) {
            Object[] paramData = { beginningParams, null };

            while (tokenizer.hasNextToken()) {
                Token token = tokenizer.nextToken();

                if (token.getToken().equals(",")) {
                    continue;
                }

                else if (token.getToken().equals(")")) {
                    break;
                }

                if (paramData[0] == null) { // In this case, we expect this token to be the type.
                    paramData[0] = token;
                }

                else { // In this case, we expect this token to be the name.
                    paramData[1] = token.getToken();

                    params.add(new Parameter((Token) paramData[0], (String) paramData[1]));

                    paramData[0] = null;
                    paramData[1] = null;
                }
            }
        }

        return new Constructor(superBlock, visibility, params.toArray(new Parameter[params.size()]));
    }
}