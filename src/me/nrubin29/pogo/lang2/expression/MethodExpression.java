package me.nrubin29.pogo.lang2.expression;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.Value;
import me.nrubin29.pogo.lang2.block.Method;

import java.io.IOException;
import java.util.List;

public class MethodExpression extends Expression {

    private Method method;
    private List<Value> values;

    public MethodExpression(Method method, List<Value> values) {
        super(null, null);

        this.method = method;
        this.values = values;
    }

    @Override
    public Value evaluate() throws IOException, InvalidCodeException {
        return method.invoke(values);
    }
}