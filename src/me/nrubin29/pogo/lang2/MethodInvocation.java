package me.nrubin29.pogo.lang2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class MethodInvocation extends ReadOnlyBlock {

    private String invokableName, methodName;
    private ArrayList<Token> values;
    private Token capture;

    public MethodInvocation(Block superBlock, String invokableName, String methodName, ArrayList<Token> values, Token capture) {
        super(superBlock);

        this.invokableName = invokableName;
        this.methodName = methodName;
        this.values = values;
        this.capture = capture;
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Class clazz;

        if (invokableName.equals("System")) {
            clazz = SystemClass.getInstance();
        }

        else if (invokableName.equals("this")) {
            clazz = (Class) getBlockTree()[0];
        }

        else {
            clazz = Runtime.RUNTIME.getPogoClass(invokableName);
        }

        if (clazz == null) {
            Optional<Variable> v = getSuperBlock().getVariable(invokableName);

            if (!v.isPresent()) {
                throw new InvalidCodeException("Expected class or variable, found " + invokableName + ".");
            }

            Variable var = v.get();

            if (var.getType() instanceof PrimitiveType) {
                throw new InvalidCodeException("Attempted to invoke method on primitive variable.");
            }

            clazz = (Class) var.getType();
        }

        Optional<Method> m = clazz.getSubBlock(Method.class, methodName);

        if (!m.isPresent()) {
            throw new InvalidCodeException("Expected method, found " + methodName + ".");
        }

        Object ret = m.get().invoke(values.stream().map(token -> {
            try {
                return Utils.handleVariables(token, getSuperBlock());
            } catch (InvalidCodeException e) {
                e.printStackTrace();
                return null;
            }
        }).filter(value -> value != null).collect(Collectors.toList()));

        if (capture != null && capture.getType() != Token.TokenType.EMPTY) {
            getSuperBlock().getVariable(capture.getToken()).get().setValue(ret);
        }
    }

    @Override
    public String toString() {
        return getClass() + " invokableName=" + invokableName + " methodName=" + methodName;
    }
}