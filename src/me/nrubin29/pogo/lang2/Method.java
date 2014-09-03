package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.ide.Console;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Method extends Block implements Nameable {

    private String name;
    private Token typeToken;
    private Type type;
    private Parameter[] parameters;
    private Value returnValue;

    public Method(Block superBlock, String name, Token typeToken, Parameter[] parameters, Token... propertyTokens) {
        super(superBlock, propertyTokens);

        this.name = name;
        this.typeToken = typeToken;
        this.parameters = parameters;
    }

    @Override
    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public void setReturnValue(Value returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        invoke(Collections.emptyList());
    }

    public Object invoke(List<Value> values) throws InvalidCodeException, IOException {
        Runtime.RUNTIME.print("invoke() called on method " + name + ".", Console.MessageType.OUTPUT);

        // TODO: Parse properties and call applyToMethod method on them if that method is defined.

        this.type = Type.match(typeToken.getToken());

        if (values.size() != parameters.length) {
            throw new InvalidCodeException("Invalid number of parameters specified.");
        }

        for (int i = 0; i < parameters.length && i < values.size(); i++) {
            Parameter p = parameters[i];
            Value v = values.get(i);

            if (!v.getType().equalsType(p.getMatchedType())) {
                throw new InvalidCodeException("Type mismatch for parameter " + p.getName() + ". Type is " + v.getType() + ". Should be " + p.getMatchedType() + ".");
            }

            addVariable(new Variable(this, p.getName(), p.getMatchedType(), v.getValue()));
        }

        for (Block block : getSubBlocks()) {
            block.run();

            if (block instanceof Return && returnValue != null) {
                break;
            }
        }

        return returnValue;
    }

    @Override
    public String toString() {
        return getClass() + " name=" + name + " typeToken=" + typeToken + " parameters=" + Arrays.toString(parameters);
    }
}
