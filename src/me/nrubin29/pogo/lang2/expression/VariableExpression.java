package me.nrubin29.pogo.lang2.expression;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.Utils;
import me.nrubin29.pogo.lang2.Value;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.tokenizer.PreProcessedTokenizer;
import me.nrubin29.pogo.lang2.tokenizer.Token;

import java.io.IOException;

public class VariableExpression extends Expression {

    private Token token;

    public VariableExpression(Token token, Block block) {
        super(new PreProcessedTokenizer(token), block);

        this.token = token;
    }

    @Override
    public Value evaluate() throws IOException, InvalidCodeException {
        return Utils.parseToken(token, block);
    }
}