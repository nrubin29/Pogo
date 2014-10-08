package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.PogoTokenizer;
import me.nrubin29.pogo.lang2.Token;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.block.VariableDeclaration;

import java.util.ArrayList;

import static me.nrubin29.pogo.lang2.Regex.IDENTIFIER;
import static me.nrubin29.pogo.lang2.Regex.PROPERTY;

public class VariableDeclarationParser extends Parser<VariableDeclaration> {

    public VariableDeclarationParser() {
        super(VariableDeclaration.class);
    }

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches("((" + PROPERTY + " )*)?" + IDENTIFIER + " " + IDENTIFIER + "( = .*)?");
    }

    @Override
    public VariableDeclaration parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException {
        // [@...] string name = "Noah"
        // [@...] Person person = new("Noah")

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

        Token type = tokenizer.nextToken();

        Token name = tokenizer.nextToken();

        Token possibleEquals = tokenizer.nextToken();

        if (possibleEquals.getToken() == null || !possibleEquals.getToken().equals("=")) {
            tokenizer.pushBack();
        }

        return new VariableDeclaration(superBlock, type, name, tokenizer, properties.toArray(new Token[properties.size()]));
    }
}