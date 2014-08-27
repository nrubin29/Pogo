package me.nrubin29.pogo.lang2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class MethodInvocation extends ReadOnlyBlock {

    private Token invokableToken, methodToken, captureToken;
    private ArrayList<Token> values;

    public MethodInvocation(Block superBlock, Token invokableToken, Token methodToken, ArrayList<Token> values, Token captureToken) {
        super(superBlock);

        this.invokableToken = invokableToken;
        this.methodToken = methodToken;
        this.values = values;
        this.captureToken = captureToken;
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Class clazz;

        if (invokableToken.getToken().equals("System")) {
            clazz = SystemClass.getInstance();
        }

        else if (invokableToken.getToken().equals("this")) {
            clazz = (Class) getBlockTree()[0];
        }

        else {
            clazz = Runtime.RUNTIME.getPogoClass(invokableToken.getToken());
        }

        if (clazz == null) {
            Optional<Variable> v = getSuperBlock().getVariable(invokableToken.getToken());

            if (!v.isPresent()) {
                throw new InvalidCodeException("Expected class or variable, found " + invokableToken + ".");
            }

            Variable var = v.get();

            if (var.getType() instanceof PrimitiveType) {
                throw new InvalidCodeException("Attempted to invoke method on primitive variable.");
            }

            clazz = (Class) var.getType();
        }

        Optional<Method> m = clazz.getSubBlock(Method.class, methodToken.getToken());

        if (!m.isPresent()) {
            throw new InvalidCodeException("Expected method, found " + methodToken + ".");
        }

        Object ret = m.get().invoke(values.stream().map(token -> {
            try {
                return Utils.parseToken(token, getSuperBlock());
            } catch (InvalidCodeException e) {
                e.printStackTrace();
                return null;
            }
        }).filter(value -> value != null).collect(Collectors.toList()));

        if (captureToken != null && captureToken.getType() != Token.TokenType.EMPTY) {
            getSuperBlock().getVariable(captureToken.getToken()).get().setValue(ret);
        }
    }

    @Override
    public String toString() {
        return getClass() + " invokableToken=" + invokableToken + " methodToken=" + methodToken + " captureToken=" + captureToken;
    }
}