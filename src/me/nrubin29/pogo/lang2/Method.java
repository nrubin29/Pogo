package me.nrubin29.pogo.lang2;

import java.util.Arrays;

public class Method extends Block implements Nameable {

    private String name;
    private Visibility visibility;
    private Type type;
    private Parameter[] parameters;

    public Method(Block superBlock, String name, Visibility visibility, Type type, Parameter... parameters) {
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
    public void run() {
        System.out.println("run() called on " + toString());

        for (Block subBlock : getSubBlocks()) {
            subBlock.run();
        }
    }

    @Override
    public String toString() {
        return getClass() + " name=" + name + " visibility=" + visibility + " type=" + type + " parameters=" + Arrays.toString(parameters);
    }
}
