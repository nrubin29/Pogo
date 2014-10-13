package me.nrubin29.pogo.lang2.system;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.Parameter;
import me.nrubin29.pogo.lang2.Type;
import me.nrubin29.pogo.lang2.Value;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.block.Method;
import me.nrubin29.pogo.lang2.tokenizer.Token;

import java.io.IOException;
import java.util.List;

public abstract class SystemMethod extends Method {

    public SystemMethod(Block superBlock, String name, Type type, Parameter... parameters) {
        super(superBlock, name, new Token(Token.TokenType.IDENTIFIER, type.toString()), parameters);
    }

    @Override
    public Value invoke(List<Value> values) throws IOException, InvalidCodeException {
        super.invoke(values);
        return invoke();
    }

    protected abstract Value invoke() throws InvalidCodeException;
}
