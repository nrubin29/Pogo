package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.Pogo;
import me.nrubin29.pogo.ide.Console;

public class While extends ConditionalBlock {

    public While(Block superBlock, Value a, Value b, Comparison comparison) {
        super(superBlock, a, b, comparison);
    }

    @Override
    public void run() throws InvalidCodeException {
        Pogo.getIDE().getConsole().write("run() called on " + toString(), Console.MessageType.OUTPUT);

        while (doComparison()) {
            Pogo.getIDE().getConsole().write("While loop about to go around once.", Console.MessageType.OUTPUT);

            for (Block subBlock : getSubBlocks()) {
                subBlock.run();
            }
        }

        Pogo.getIDE().getConsole().write("While loop finished.", Console.MessageType.OUTPUT);
    }
}