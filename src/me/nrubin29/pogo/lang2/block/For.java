package me.nrubin29.pogo.lang2.block;

import me.nrubin29.pogo.lang2.*;
import me.nrubin29.pogo.lang2.tokenizer.Token;

import java.io.IOException;

public class For extends Block implements Endable {

    private Token lower, upper;

    public For(Block superBlock, Token lower, Token upper) {
        super(superBlock);

        this.lower = lower;
        this.upper = upper;
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Value l = checkBound(lower), u = checkBound(upper);

        double lVal = 0, uVal = 0;

        if (l.getType() == PrimitiveType.DOUBLE) {
            lVal = (double) l.getValue();
        }

        else if (l.getType() == PrimitiveType.INTEGER) {
            lVal = (int) l.getValue();
        }

        if (u.getType() == PrimitiveType.DOUBLE) {
            uVal = (double) u.getValue();
        }

        else if (u.getType() == PrimitiveType.INTEGER) {
            uVal = (int) u.getValue();
        }

        int direction = uVal > lVal ? 1 : -1;

        for (double i = lVal; i < uVal; i += direction) {
            if (getSuperBlock().hasVariable(lower.getToken())) {
                getSuperBlock().getVariable(lower.getToken()).get().setValue(i);
            }

            for (Block subBlock : getSubBlocks()) {
                subBlock.run();
            }
        }
    }

    private Value checkBound(Token bound) throws InvalidCodeException {
        if (bound.getType() == Token.TokenType.DOUBLE_LITERAL) {
            return new Value(PrimitiveType.DOUBLE, bound.getToken());
        }

        else if (bound.getType() == Token.TokenType.INTEGER_LITERAL) {
            return new Value(PrimitiveType.INTEGER, bound.getToken());
        }

        else if (bound.getType() == Token.TokenType.STRING_LITERAL) {
            Value value = Utils.parseToken(bound, this);

            if (value.getType() == PrimitiveType.DOUBLE || value.getType() == PrimitiveType.INTEGER) {
                return value;
            }

            else {
                throw new InvalidCodeException("Expected number or variable, got " + bound + ".");
            }
        }

        else {
            throw new InvalidCodeException("Expected number or variable, got " + bound + ".");
        }
    }

    @Override
    public String toString() {
        return getClass() + " lower=" + lower + " upper=" + upper;
    }
}