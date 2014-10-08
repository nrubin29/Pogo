package me.nrubin29.pogo.lang2.block;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.Nameable;
import me.nrubin29.pogo.lang2.Token;
import me.nrubin29.pogo.lang2.Variable;
import me.nrubin29.pogo.lang2.expression.Expression;

import java.io.IOException;
import java.util.Optional;

public class VariableReassignment extends ReadOnlyBlock implements Nameable {

    private Token nameToken;
    private Expression expression;

    public VariableReassignment(Block superBlock, Token nameToken, Expression expression) {
        super(superBlock);

        this.nameToken = nameToken;
        this.expression = expression;
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Optional<Variable> v = getVariable(nameToken.getToken());

        if (!v.isPresent()) {
            throw new InvalidCodeException("Variable " + nameToken.getToken() + " could not be reassigned because it is not defined in this scope.");
        }

        else {
            v.get().setValue(expression.evaluate().getValue());
        }
    }

    @Override
    public String getName() {
        return nameToken.getToken();
    }

    @Override
    public String toString() {
        return getClass() + " nameToken=" + nameToken;
    }
}