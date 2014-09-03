package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.Block;
import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.PogoTokenizer;
import me.nrubin29.pogo.lang2.Property;

import static me.nrubin29.pogo.lang2.Regex.IDENTIFIER;

public class PropertyParser extends Parser<Property> {

    public PropertyParser() {
        super(Property.class);
    }

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches("^(property " + IDENTIFIER + ")");
    }

    @Override
    public Property parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException {
        tokenizer.nextToken(); // Skip the class token.
        return new Property(tokenizer.nextToken().getToken());
    }
}