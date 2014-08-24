package me.nrubin29.pogo.lang2;

import java.io.IOException;
import java.io.StreamTokenizer;

public class VariableDeclaration extends ReadOnlyBlock implements Nameable {

    private String typeName, nameName;
    private boolean init, newValue;
    private StreamTokenizer value;

    public VariableDeclaration(Block superBlock, String typeName, String nameName, boolean init, boolean newValue, StreamTokenizer value) {
        super(superBlock);

        this.typeName = typeName;
        this.nameName = nameName;
        this.init = init;
        this.newValue = newValue;
        this.value = value;
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Type type = Type.match(typeName);

        if (type == null) {
            throw new InvalidCodeException("Expected type, got " + typeName + ".");
        }

        if (type == PrimitiveType.VOID) {
            throw new InvalidCodeException("Attempted to instantiate variable with type void.");
        }

        Variable variable = new Variable(getSuperBlock(), nameName, type);

        if (init) {
            if (newValue) {
                if (type instanceof PrimitiveType) {
                    throw new InvalidCodeException("Attempted to instantiate primitive type with new.");
                }

                else {
                    variable.setValue(((Class) type).clone());
                }
            }

            else {
                variable.setValue(Utils.handleVariables(value, getSuperBlock()).getValue());
            }
        }

        getSuperBlock().addVariable(variable);
    }

    @Override
    public String getName() {
        return nameName;
    }

    public String getType() {
        return typeName;
    }

    public boolean hasValue() {
        return init && (newValue || value != null);
    }

    public boolean isValueNew() {
        return newValue;
    }

    public StreamTokenizer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getClass() + " typeName=" + typeName + " nameName=" + nameName + " init=" + init + " newValue=" + newValue + " value=" + value;
    }
}