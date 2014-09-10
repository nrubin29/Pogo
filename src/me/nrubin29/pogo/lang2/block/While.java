package me.nrubin29.pogo.lang2.block;

import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang2.Condition;
import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.Runtime;

import java.io.IOException;

public class While extends ConditionalBlock {

    public While(Block superBlock, Condition... conditions) {
        super(superBlock, conditions);
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Runtime.RUNTIME.print("run() called on " + toString(), Console.MessageType.OUTPUT);

        while (areConditionsTrue(this)) {
            Runtime.RUNTIME.print("While loop about to go around once.", Console.MessageType.OUTPUT);

            for (Block subBlock : getSubBlocks()) {
                subBlock.run();
            }
        }

        Runtime.RUNTIME.print("While loop finished.", Console.MessageType.OUTPUT);
    }
}