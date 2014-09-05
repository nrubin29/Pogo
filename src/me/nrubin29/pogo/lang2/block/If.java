package me.nrubin29.pogo.lang2.block;

import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang2.Comparison;
import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.Runtime;
import me.nrubin29.pogo.lang2.Value;

import java.io.IOException;
import java.util.ArrayList;

public class If extends ConditionalBlock {

    private ArrayList<ElseIf> elseIfs;
    private Else elze;

    public If(Block superBlock, Value a, Value b, Comparison comparison) {
        super(superBlock, a, b, comparison);

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
        Runtime.RUNTIME.print("doComparison() -> " + doComparison(), Console.MessageType.OUTPUT);

        if (doComparison()) {
            for (Block subBlock : getSubBlocks()) {
                subBlock.run();
            }
        }

        else {
            for (ElseIf elseIf : elseIfs) {
                if (elseIf.doComparison()) {
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