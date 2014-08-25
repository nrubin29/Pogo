package me.nrubin29.pogo.lang2;

import java.io.IOException;

/**
 * Represents a return value from a method.
 * At runtime, when one of these is encountered, the method stops whereever it is and returns the value.
 */
public class Return extends ReadOnlyBlock {

    private Token value;

    public Return(Block superBlock, Token value) {
        super(superBlock);

        this.value = value;
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Method method = (Method) getBlockTree()[1]; // [0] is the class, [1] is the method.
        method.setReturnValue(Utils.handleVariables(value, getSuperBlock()));
    }

    @Override
    public String toString() {
        return getClass() + " value=" + value;
    }
}