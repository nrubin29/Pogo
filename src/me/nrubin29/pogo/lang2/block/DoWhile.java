package me.nrubin29.pogo.lang2.block;

import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang2.Condition;
import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.Runtime;

import java.io.IOException;

public class DoWhile extends ConditionalBlock {

    public DoWhile(Block superBlock, Condition... conditions) {
        super(superBlock, conditions);
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Runtime.RUNTIME.print("run() called on " + toString(), Console.MessageType.OUTPUT);

        do {
            Runtime.RUNTIME.print("Do while loop about to go around once.", Console.MessageType.OUTPUT);

            for (Block subBlock : getSubBlocks()) {
                subBlock.run();
            }
        } while (areConditionsTrue(this));

        Runtime.RUNTIME.print("Do while loop finished.", Console.MessageType.OUTPUT);
    }
}