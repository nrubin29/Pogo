package me.nrubin29.pogo.lang2;

import java.util.ArrayList;
import java.util.Arrays;

public class Method extends Block implements Nameable {

    private String name;
    private Visibility visibility;
    private Type type;
    private Parameter[] parameters;

    public Method(Block superBlock, String name, Visibility visibility, Type type, Parameter[] parameters) {
        super(superBlock);

        this.name = name;
        this.visibility = visibility;
        this.type = type;
        this.parameters = parameters;
    }

    @Override
    public String getName() {
        return name;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public Type getType() {
        return type;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    @Override
    public void run() throws InvalidCodeException {
        if (!name.equals("main") && visibility != Visibility.PUBLIC && type != PrimitiveType.VOID && parameters.length > 0) {
            throw new UnsupportedOperationException("Cannot use run() on non-main method. Use invoke()");
        }
    }

    public void invoke(ArrayList<Value> values) throws InvalidCodeException {
        for (Block block : getSubBlocks()) {
            block.run();
        }
    }

    @Override
    public String toString() {
        return getClass() + " name=" + name + " visibility=" + visibility + " type=" + type + " parameters=" + Arrays.toString(parameters);
    }
}
