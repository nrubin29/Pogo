package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import java.io.IOException;

import static me.nrubin29.pogo.lang2.Regex.COMPARISON;
import static me.nrubin29.pogo.lang2.Regex.IDENTIFIER_OR_LITERAL;

public class IfParser extends Parser<Block> {

    private If lastIf;

    @Override
    public boolean shouldParseLine(String line) {
        return line.equals("else") || line.matches("(if|elseif) \\(" + IDENTIFIER_OR_LITERAL + " " + COMPARISON + " " + IDENTIFIER_OR_LITERAL + "\\)");
    }

    @Override
    public Block parse(Block superBlock, PogoTokenizer tokenizer) throws IOException, InvalidCodeException {
        // if|elseif|else ((name == "Noah"))?

        String type = tokenizer.nextToken().getToken();

        if (type.equals("elseif") && lastIf == null) {
            throw new InvalidCodeException("Attempted to write elseif statement without if statement.");
        }

        if (type.equals("else")) {
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

        if (!tokenizer.nextToken().getToken().equals("(")) {
            throw new InvalidCodeException("If statement does not begin with opening parenthesis.");
        }

        Value a = Utils.parseToken(tokenizer.nextToken(), superBlock);

        Comparison comparison = Comparison.valueOfToken(tokenizer.nextToken().getToken());

        Value b = Utils.parseToken(tokenizer.nextToken(), superBlock);

        if (!tokenizer.nextToken().getToken().equals(")")) {
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