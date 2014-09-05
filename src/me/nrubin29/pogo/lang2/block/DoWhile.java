package me.nrubin29.pogo.lang2.block;

import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang2.Comparison;
import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.Runtime;
import me.nrubin29.pogo.lang2.Value;

import java.io.IOException;

public class DoWhile extends ConditionalBlock {

    public DoWhile(Block superBlock, Value a, Value b, Comparison comparison) {
        super(superBlock, a, b, comparison);
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        me.nrubin29.pogo.lang2.Runtime.RUNTIME.print("run() called on " + toString(), Console.MessageType.OUTPUT);

        do {
            Runtime.RUNTIME.print("Do while loop about to go around once.", Console.MessageType.OUTPUT);

            for (Block subBlock : getSubBlocks()) {
                subBlock.run();
            }
        } while (doComparison());

        Runtime.RUNTIME.print("Do while loop finished.", Console.MessageType.OUTPUT);
    }
}