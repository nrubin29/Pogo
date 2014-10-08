package me.nrubin29.pogo.lang2.expression;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.Token;
import me.nrubin29.pogo.lang2.Utils;
import me.nrubin29.pogo.lang2.Value;
import me.nrubin29.pogo.lang2.block.Block;

import java.io.IOException;

public class VariableExpression extends Expression {

    private Token token;
    private Block block;

    public VariableExpression(Token token, Block block) {
        this.token = token;
        this.block = block;
    }

    @Override
    public Value evaluate() throws IOException, InvalidCodeException {
        return Utils.parseToken(token, block);
    }
}