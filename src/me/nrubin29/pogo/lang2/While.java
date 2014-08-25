package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.ide.Console;

import java.io.IOException;

public class While extends ConditionalBlock {

    public While(Block superBlock, Value a, Value b, Comparison comparison) {
        super(superBlock, a, b, comparison);
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Runtime.RUNTIME.print("run() called on " + toString(), Console.MessageType.OUTPUT);

        while (doComparison()) {
            Runtime.RUNTIME.print("While loop about to go around once.", Console.MessageType.OUTPUT);

            for (Block subBlock : getSubBlocks()) {
                subBlock.run();
            }
        }

        Runtime.RUNTIME.print("While loop finished.", Console.MessageType.OUTPUT);
    }
}