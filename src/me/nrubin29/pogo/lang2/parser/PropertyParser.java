package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.block.Property;
import me.nrubin29.pogo.lang2.tokenizer.Tokenizer;

import static me.nrubin29.pogo.lang2.tokenizer.Regex.IDENTIFIER;

public class PropertyParser extends Parser<Property> {

    public PropertyParser() {
        super(Property.class);
    }

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches("^(property " + IDENTIFIER + ")");
    }

    @Override
    public Property parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
        tokenizer.nextToken(); // Skip the class token.
        return new Property(tokenizer.nextToken().getToken());
    }
}