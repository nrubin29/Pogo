package me.nrubin29.pogo.lang2.block;

import me.nrubin29.pogo.lang2.Condition;
import me.nrubin29.pogo.lang2.Endable;
import me.nrubin29.pogo.lang2.InvalidCodeException;

import java.util.Arrays;

public abstract class ConditionalBlock extends Block implements Endable {

    private Condition[] conditions;

    public ConditionalBlock(Block superBlock, Condition... conditions) {
        super(superBlock);

        this.conditions = conditions;
    }

    public boolean areConditionsTrue(Block block) throws InvalidCodeException {
        for (Condition condition : conditions) {
            if (!condition.isConditionTrue(block)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return getClass() + " conditions=" + Arrays.toString(conditions);
    }
}