package me.nrubin29.pogo.lang2.block;

import me.nrubin29.pogo.lang2.*;

public abstract class ConditionalBlock extends Block implements Endable {

    private Value a;
    private Value b;
    private Comparison comparison;

    public ConditionalBlock(Block superBlock, Value a, Value b, Comparison comparison) {
        super(superBlock);

        this.a = a;
        this.b = b;
        this.comparison = comparison;
    }

    public Value getA() {
        return a;
    }

    public Value getB() {
        return b;
    }

    public Comparison getComparison() {
        return comparison;
    }

    protected boolean doComparison() throws InvalidCodeException {
        boolean success;

        if (getComparison() == Comparison.EQUALS) {
            success = getA().equals(getB());
        }

        else if (getComparison() == Comparison.NOTEQUALS) {
            success = !getA().equals(getB());
        }

        else {
            if (!getA().getType().equals(PrimitiveType.DOUBLE) && !getA().getType().equals(PrimitiveType.INTEGER)) {
                throw new InvalidCodeException("Attempted to use " + getComparison() + " with non-number first value.");
            }

            if (!getB().getType().equals(PrimitiveType.DOUBLE) && !getB().getType().equals(PrimitiveType.INTEGER)) {
                throw new InvalidCodeException("Attempted to use " + getComparison() + " with non-number second value.");
            }

            double aDouble = (double) getA().getValue(), bDouble = (double) getB().getValue();

            if (getComparison() == Comparison.GREATERTHAN) {
                success = aDouble > bDouble;
            }

            else if (getComparison() == Comparison.LESSTHAN) {
                success = aDouble < bDouble;
            }

            else if (getComparison() == Comparison.GREATERTHANEQUALTO) {
                success = aDouble >= bDouble;
            }

            else if (getComparison() == Comparison.LESSTHANEQUALTO) {
                success = aDouble <= bDouble;
            }

            else {
                throw new InvalidCodeException(getComparison() + " is not supported.");
            }
        }

        return success;
    }

    @Override
    public String toString() {
        return getClass() + " a=" + a + " b=" + b;
    }
}