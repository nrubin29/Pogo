package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Utils;

public class For extends Block {

    private final String lower, upper;

    public For(Block superBlock, String lower, String upper) {
        super(superBlock);

        this.lower = lower;
        this.upper = upper;
    }

    @Override
    public void runAfterParse() throws InvalidCodeException {
        double a, b;

        try {
            a = Double.valueOf(Utils.implode(lower, this));
            b = Double.valueOf(Utils.implode(upper, this));
        } catch (Exception e) {
            throw new InvalidCodeException("Attempted to use for loop with non-number bounds.");
        }

        double larger = Math.max(a, b), smaller = Math.min(a, b);

        for (double i = smaller; i < larger; i++) {
            try {
                getSuperBlock().getVariable(lower).setValue(i);
            } catch (Exception ignored) {
            }

            doBlocks();
        }
    }

    @Override
    public String toString() {
        return "For lower=" + lower + " upper=" + upper;
    }
}