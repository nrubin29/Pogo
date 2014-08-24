package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import java.io.IOException;
import java.io.StreamTokenizer;

public class IfParser extends Parser<Block> {

    private If lastIf;

    @Override
    public boolean shouldParse(String firstToken) {
        return firstToken.equals("if") || firstToken.equals("elseif") || firstToken.equals("else");
    }

    @Override
    public Block parse(Block superBlock, StreamTokenizer tokenizer) throws IOException, InvalidCodeException {
        // if|elseif|else (name == "Noah")

        tokenizer.nextToken();

        if (tokenizer.sval.equals("elseif") && lastIf == null) {
            throw new InvalidCodeException("Attempted to write elseif statement without if statement.");
        }

        if (tokenizer.sval.equals("else")) {
            if (lastIf == null) {
                throw new InvalidCodeException("Attempted to write else statement without if statement.");
            }

            else {
                Else elze = new Else(superBlock);
                lastIf.setElse(elze);
                lastIf = null;
                return elze;
            }
        }

        String type = tokenizer.sval; // Either if or elseif.

        tokenizer.nextToken();

        if (tokenizer.ttype != '(') {
            throw new InvalidCodeException("If statement does not begin with opening parenthesis.");
        }

        tokenizer.nextToken();

        Value a = Utils.handleVariables(tokenizer.sval, tokenizer.ttype, superBlock);

        tokenizer.nextToken();

        char firstComparisonToken = (char) tokenizer.ttype;

        tokenizer.nextToken();

        char secondComparisonToken = (char) tokenizer.ttype;

        Comparison comparison = Comparison.valueOfToken(firstComparisonToken + "" + secondComparisonToken);

        tokenizer.nextToken();

        Value b = Utils.handleVariables(tokenizer.sval, tokenizer.ttype, superBlock);

        tokenizer.nextToken();

        if (tokenizer.ttype != ')') {
            throw new InvalidCodeException("If statement does not end with closing parenthesis.");
        }

        if (type.equals("if")) {
            return lastIf = new If(superBlock, a, b, comparison);
        }

        else {
            return lastIf.addElseIf(new ElseIf(superBlock, a, b, comparison));
        }
    }
}