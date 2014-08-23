package me.nrubin29.pogo.lang2;

import java.util.Optional;

public class MethodInvocation extends Block {

    private String invokableName, methodName;

    public MethodInvocation(Block superBlock, String invokableName, String methodName) {
        super(superBlock);

        this.invokableName = invokableName;
        this.methodName = methodName;
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

        m.get().run();
    }

    @Override
    public String toString() {
        return getClass() + " invokableName=" + invokableName + " methodName=" + methodName;
    }

    @Override
    public void add(Block subBlock) {
        throw new UnsupportedOperationException("Method invocation cannot have subblocks.");
    }

    @Override
    public <T extends Block & Nameable> Optional<T> getSubBlock(java.lang.Class<T> clazz, String name) {
        throw new UnsupportedOperationException("Method invocation cannot have subblocks.");
    }

    @Override
    public <T extends Block & Nameable> boolean hasSubBlock(java.lang.Class<T> clazz, String name) {
        throw new UnsupportedOperationException("Method invocation cannot have subblocks.");
    }

    @Override
    public Optional<Variable> getVariable(String name) {
        throw new UnsupportedOperationException("Method invocation cannot have variables");
    }

    public boolean hasVariable(String name) {
        throw new UnsupportedOperationException("Method invocation cannot have variables");
    }

    public void addVariable(Variable variable) {
        throw new UnsupportedOperationException("Method invocation cannot have variables");
    }
}