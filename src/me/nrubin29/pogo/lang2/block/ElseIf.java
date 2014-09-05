package me.nrubin29.pogo.lang2.block;

import me.nrubin29.pogo.lang2.Comparison;
import me.nrubin29.pogo.lang2.Value;

public class ElseIf extends ConditionalBlock {

    public ElseIf(Block superBlock, Value a, Value b, Comparison comparison) {
        super(superBlock, a, b, comparison);
    }

    @Override
    public void run() {
        // We don't want to do anything because the If will run this.
    }
}