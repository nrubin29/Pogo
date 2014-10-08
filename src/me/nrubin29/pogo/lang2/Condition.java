package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.lang2.expression.Expression;

import java.io.IOException;

public class Condition {

    private Expression a, b;
    private Comparison comparison;

    public Condition(Expression a, Expression b, Comparison comparison) {
        this.a = a;
        this.b = b;
        this.comparison = comparison;
    }

    public boolean isConditionTrue() throws InvalidCodeException, IOException {
        Value a = this.a.evaluate(), b = this.b.evaluate();

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