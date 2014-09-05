package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.Parameter;
import me.nrubin29.pogo.lang2.PogoTokenizer;
import me.nrubin29.pogo.lang2.Token;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.block.Method;

import java.util.ArrayList;

import static me.nrubin29.pogo.lang2.Regex.IDENTIFIER;
import static me.nrubin29.pogo.lang2.Regex.PROPERTY;

public class MethodParser extends Parser<Method> {

    public MethodParser() {
        super(Method.class);
    }

    @Override
    public boolean shouldParseLine(String line) {
        // TODO: Fix validity of leading comma in parameter list.
        return line.matches("((" + PROPERTY + " )*)?" + "method " + IDENTIFIER + "( )?=( )?\\((" + IDENTIFIER + " " + IDENTIFIER + ")?((,( )?" + IDENTIFIER + " " + IDENTIFIER + ")?)*\\)( )?->( )?" + IDENTIFIER);
    }

    @Override
    public Method parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException {
        // [@...] method main = () -> void

        ArrayList<Token> properties = new ArrayList<>();

        while (tokenizer.hasNextToken()) {
            Token token = tokenizer.nextToken();

            if (token.getType() == Token.TokenType.PROPERTY) {
                properties.add(token);
            }

            else {
                tokenizer.pushBack();
                break;
            }
        }

        tokenizer.nextToken(); // Skip the method token.

        String methodName = tokenizer.nextToken().getToken();

        tokenizer.nextToken(); // Skip the = token.

        if (!tokenizer.nextToken().getToken().equals("(")) {
            tokenizer.pushBack();
            throw new InvalidCodeException("Method declaration missing parentheses. Got " + tokenizer.nextToken() + " instead.");
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

        tokenizer.nextToken(); // Skip the -> token.

        Token returnToken = tokenizer.nextToken();

        return new Method(superBlock, methodName, returnToken, params.toArray(new Parameter[params.size()]), properties.toArray(new Token[properties.size()]));
    }
}