package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.Pogo;
import me.nrubin29.pogo.ide.Console;

public class If extends ConditionalBlock {

    public If(Block superBlock, ConditionalBlockType type, Value a, Value b, Comparison comparison) {
        super(superBlock, type, a, b, comparison);
    }

    @Override
    public void run() throws InvalidCodeException {
        Pogo.getIDE().getConsole().write("run() called on " + toString(), Console.MessageType.OUTPUT);
        Pogo.getIDE().getConsole().write("doComparison() -> " + doComparison(), Console.MessageType.OUTPUT);

        if (doComparison()) {
            for (Block subBlock : getSubBlocks()) {
                subBlock.run();
            }
        }
    }
}