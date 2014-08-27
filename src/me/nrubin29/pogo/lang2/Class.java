package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.ide.Console;

import java.io.IOException;
import java.util.Optional;

public class Class extends Block implements Type, Nameable, Cloneable {

    private String name;

    public Class(String name) {
        super(null);

        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Runtime.RUNTIME.print("run() called on class " + name + ".", Console.MessageType.OUTPUT);

        getMethod("main").get().run();
    }

    public Optional<Method> getMethod(String name, Type... paramTypes) {
        return getSubBlocks(Method.class).stream()
                .filter(method -> method.getName().equals(name))
                .filter(method -> {
                    try {
                        if (method.getParameters().length != paramTypes.length) {
                            return false;
                        }

                        for (int i = 0; i < method.getParameters().length && i < paramTypes.length; i++) {
                            if (!method.getParameters()[i].getMatchedType().equals(paramTypes[i])) {
                                return false;
                            }
                        }

                        return true;
                    }

                    catch (InvalidCodeException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .findFirst();
    }

    public boolean hasMethod(String name, Type... paramTypes) {
        return getMethod(name, paramTypes).isPresent();
    }

    public Optional<Constructor> getConstructor(PogoTokenizer tokenizer, Block block) throws InvalidCodeException {
        Constructor constructor = null;

        for (Constructor c : getSubBlocks(Constructor.class)) {
            if (c.getParameters().length == tokenizer.getNumberOfTokens(Token.TokenType.NOT_TOKEN)) {
                for (int i = 0; i < c.getParameters().length && tokenizer.hasNextToken(); i++) {
                    Parameter p = c.getParameters()[i];
                    Token t = tokenizer.nextToken();

                    if (t.getToken().equals(",")) {
                        continue;
                    }

                    else if (t.getToken().equals(")")) {
                        break;
                    }

                    Value v;

                    if (t.getType() == Token.TokenType.IDENTIFIER) {
                        v = Utils.parseToken(t, block);
                    }

                    else {
                        v = new Value(t.getType().getPrimitiveType(), t.getToken());
                    }

                    if (!p.getMatchedType().equals(v.getType())) {
                        throw new InvalidCodeException("Type mismatch for parameter " + p.getName() + ". Type is " + t.getType() + ". Should be " + p.getMatchedType() + ".");
                    }
                }

                constructor = c;
                break;
            }
        }

        return Optional.ofNullable(constructor);
    }

    @Override
    public String toString() {
        return getClass() + " name=" + name;
    }

    @Override
    @SuppressWarnings({"CloneDoesntCallSuperClone", "CloneDoesntDeclareCloneNotSupportedException"})
    public Class clone() {
        return this;
    }
}
