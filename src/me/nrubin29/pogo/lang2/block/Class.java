package me.nrubin29.pogo.lang2.block;

import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang2.*;
import me.nrubin29.pogo.lang2.tokenizer.Token;
import me.nrubin29.pogo.lang2.tokenizer.Tokenizer;

import java.io.IOException;
import java.util.Optional;

public class Class extends RootBlock implements Type, Cloneable {

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
        me.nrubin29.pogo.lang2.Runtime.RUNTIME.print("run() called on class " + name + ".", Console.MessageType.OUTPUT);

        getMethod("main").get().run();
    }

    public Optional<Constructor> getConstructor(Tokenizer tokenizer, Block block) throws InvalidCodeException {
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

                    if (!v.getType().equalsType(p.getMatchedType())) {
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
    public boolean equalsType(Type other) {
        return other == PrimitiveType.OBJECT || (other instanceof Class && ((Class) other).getName().equals(name));
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Type && equalsType((Type) other);
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
