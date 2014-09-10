package me.nrubin29.pogo.lang2.block;

import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang2.Condition;
import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.Runtime;

import java.io.IOException;
import java.util.ArrayList;

public class If extends ConditionalBlock {

    private ArrayList<ElseIf> elseIfs;
    private Else elze;

    public If(Block superBlock, Condition... conditions) {
        super(superBlock, conditions);

        this.elseIfs = new ArrayList<>();
    }

    public ElseIf addElseIf(ElseIf elseIf) {
        elseIfs.add(elseIf);
        return elseIf;
    }

    public void setElse(Else elze) {
        this.elze = elze;
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        me.nrubin29.pogo.lang2.Runtime.RUNTIME.print("run() called on " + toString(), Console.MessageType.OUTPUT);
        Runtime.RUNTIME.print("areConditionsTrue() -> " + areConditionsTrue(this), Console.MessageType.OUTPUT);

        if (areConditionsTrue(this)) {
            for (Block subBlock : getSubBlocks()) {
                subBlock.run();
            }
        }

        else {
            for (ElseIf elseIf : elseIfs) {
                if (elseIf.areConditionsTrue(elseIf)) {
                    for (Block subBlock : elseIf.getSubBlocks()) {
                        subBlock.run();
                    }
                    return;
                }
            }

            if (elze != null) {
                for (Block subBlock : elze.getSubBlocks()) {
                    subBlock.run();
                }
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + " elseIfsSize=" + elseIfs.size() + " hasElse=" + (elze != null);
    }
}