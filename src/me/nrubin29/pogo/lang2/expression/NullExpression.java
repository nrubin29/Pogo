package me.nrubin29.pogo.lang2.expression;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.Value;

import java.io.IOException;

public class NullExpression extends Expression {

    public NullExpression() {
        super(null, null);
    }

    @Override
    public Value evaluate() throws IOException, InvalidCodeException {
        return new Value(null);
    }
}