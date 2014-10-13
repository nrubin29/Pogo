package me.nrubin29.pogo.lang2.block;

import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang2.*;
import me.nrubin29.pogo.lang2.Runtime;
import me.nrubin29.pogo.lang2.system.MethodMeta;
import me.nrubin29.pogo.lang2.tokenizer.Token;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public Value invoke(List<Value> values) throws InvalidCodeException, IOException {
        Runtime.RUNTIME.print("invoke() called on method " + name + ".", Console.MessageType.OUTPUT);

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

        for (Token token : getPropertyTokens()) {
            Property property = Runtime.RUNTIME.getPogoClass(token.getToken().substring(token.getToken().indexOf('@') + 1));

            if (property == null) {
                throw new InvalidCodeException("Expected property, found " + token.getToken().substring(token.getToken().indexOf('@') + 1) + ".");
            }

            addProperty(property);

            Optional<Method> method = property.getMethod("applyToMethod", MethodMeta.TYPE);

            if (!method.isPresent()) {
                throw new InvalidCodeException("Property is not applicable to methods.");
            }

            method.get().invoke(Arrays.asList(new Value(MethodMeta.TYPE, new MethodMeta(this))));
        }

        for (Block block : getSubBlocks()) {
            block.run();

            if (returnValue != null) {
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
