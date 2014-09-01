package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;

import static me.nrubin29.pogo.lang2.Regex.IDENTIFIER;

public class VariableDeclarationParser extends Parser<VariableDeclaration> {

    public VariableDeclarationParser() {
        super(VariableDeclaration.class);
    }

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches(IDENTIFIER + " " + IDENTIFIER + "( = .*)?");
    }

    @Override
    public VariableDeclaration parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException {
        // string name = "Noah"
        // Person person = new("Noah")

        Token type = tokenizer.nextToken();

        Token name = tokenizer.nextToken();

        boolean init = false;

        Token possibleEquals = tokenizer.nextToken();

        if (possibleEquals.getToken() != null && possibleEquals.getToken().equals("=")) {
            init = true;
        }

        return new VariableDeclaration(superBlock, type, name, init, tokenizer);
    }
}