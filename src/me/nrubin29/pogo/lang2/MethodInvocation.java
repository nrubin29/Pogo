package me.nrubin29.pogo.lang2;

import java.util.ArrayList;
import java.util.Optional;

public class MethodInvocation extends ReadOnlyBlock {

    private String invokableName, methodName;
    private ArrayList<Value> values;

    public MethodInvocation(Block superBlock, String invokableName, String methodName, ArrayList<Value> values) {
        super(superBlock);

        this.invokableName = invokableName;
        this.methodName = methodName;
        this.values = values;
    }

    @Override
    public void run() throws InvalidCodeException {
        Class clazz;

        if (invokableName.equals("System")) {
            clazz = SystemClass.getInstance();
        }

        else {
            clazz = IDEInstance.CURRENT_INSTANCE.getPogoClass(invokableName);
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

        m.get().invoke(values);
    }

    @Override
    public String toString() {
        return getClass() + " invokableName=" + invokableName + " methodName=" + methodName;
    }
}