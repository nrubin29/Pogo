package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.ide.Console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constructor extends Block {

    private Visibility visibility;
    private Parameter[] parameters;

    public Constructor(Block superBlock, Visibility visibility, Parameter... parameters) {
        super(superBlock);

        this.visibility = visibility;
        this.parameters = parameters;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        invoke(new ArrayList<>());
    }

    public void invoke(List<Value> values) throws InvalidCodeException, IOException {
        Runtime.RUNTIME.print("invoke() called on constructor.", Console.MessageType.OUTPUT);

        if (values.size() != parameters.length) {
            throw new InvalidCodeException("Invalid number of parameters specified.");
        }

        for (int i = 0; i < parameters.length && i < values.size(); i++) {
            Parameter p = parameters[i];
            Value v = values.get(i);

            if (!v.getType().equals(p.getMatchedType())) {
                throw new InvalidCodeException("Type mismatch for parameter " + p.getName() + ". Type is " + v.getType() + ". Should be " + p.getUnmatchedType() + ".");
            }

            addVariable(new Variable(this, p.getName(), p.getMatchedType(), v.getValue()));
        }

        for (Block block : getSubBlocks()) {
            block.run();
        }
    }

    @Override
    public String toString() {
        return getClass() + " visibility=" + visibility + " parameters=" + Arrays.toString(parameters);
    }
}
