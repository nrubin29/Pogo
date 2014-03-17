package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

public class Foreach extends Block {

    private final String varName, arrayName;

    // foreach num numbers
    public Foreach(Block superBlock, String varName, String arrayName) {
        super(superBlock);

        this.varName = varName;
        this.arrayName = arrayName;
    }

    @Override
    public void runAfterParse() throws InvalidCodeException {
        Variable arrayVar = getSuperBlock().getVariable(arrayName);

        if (!arrayVar.isArray()) throw new InvalidCodeException("Attempted to use foreach on non-array");

        addVariable(arrayVar.getType(), varName, false);

        for (Object val : arrayVar.getValues()) {
            getVariable(varName).setValue(val);
            doBlocks();
        }
    }

    @Override
    public String toString() {
        return "Foreach varName=" + varName + " arrayName=" + arrayName;
    }
}