package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.lang2.block.Block;

public class Condition {

    private Token a, b;
    private Comparison comparison;

    public Condition(Token a, Token b, Comparison comparison) {
        this.a = a;
        this.b = b;
        this.comparison = comparison;
    }

    public boolean isConditionTrue(Block block) throws InvalidCodeException {
        Value a = Utils.parseToken(this.a, block), b = Utils.parseToken(this.b, block);

        boolean success;

        if (comparison == Comparison.EQUALS) {
            success = a.equals(b);
        }

        else if (comparison == Comparison.NOTEQUALS) {
            success = !a.equals(b);
        }

        else {
            if (!a.getType().equals(PrimitiveType.DOUBLE) && !a.getType().equals(PrimitiveType.INTEGER)) {
                throw new InvalidCodeException("Attempted to use " + comparison + " with non-number first value.");
            }

            if (!b.getType().equals(PrimitiveType.DOUBLE) && !b.getType().equals(PrimitiveType.INTEGER)) {
                throw new InvalidCodeException("Attempted to use " + comparison + " with non-number second value.");
            }

            double aDouble = (double) a.getValue(), bDouble = (double) b.getValue();

            if (comparison == Comparison.GREATERTHAN) {
                success = aDouble > bDouble;
            }

            else if (comparison == Comparison.LESSTHAN) {
                success = aDouble < bDouble;
            }

            else if (comparison == Comparison.GREATERTHANEQUALTO) {
                success = aDouble >= bDouble;
            }

            else if (comparison == Comparison.LESSTHANEQUALTO) {
                success = aDouble <= bDouble;
            }

            else {
                throw new InvalidCodeException(comparison + " is not supported.");
            }
        }

        return success;
    }

    @Override
    public String toString() {
        return getClass() + " a=" + a + " b=" + b + " comparison=" + comparison;
    }
}