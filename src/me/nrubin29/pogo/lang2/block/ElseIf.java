package me.nrubin29.pogo.lang2.block;

import me.nrubin29.pogo.lang2.Condition;

public class ElseIf extends ConditionalBlock {

    public ElseIf(Block superBlock, Condition... conditions) {
        super(superBlock, conditions);
    }

    @Override
    public void run() {
        // We don't want to do anything because the If will run this.
    }
}