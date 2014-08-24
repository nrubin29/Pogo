package me.nrubin29.pogo.lang2;

public class ElseIf extends ConditionalBlock {

    public ElseIf(Block superBlock, Value a, Value b, Comparison comparison) {
        super(superBlock, a, b, comparison);
    }

    @Override
    public void run() throws InvalidCodeException {
        // We don't want to do anything because the If will run this.
    }
}