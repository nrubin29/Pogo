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

    public Optional<Constructor> getConstructor(PogoTokenizer tokens, Block block) throws InvalidCodeException {
        Constructor constructor = null;

        for (Constructor c : getSubBlocks(Constructor.class)) {
            for (int i = 0; i < c.getParameters().length && tokens.hasNextToken(); i++) {
                Parameter p = c.getParameters()[i];
                Token t = tokens.nextToken();

                if (t.getToken().equals(")")) {
                    break;
                }

                Value v;

                if (t.getType() == Token.TokenType.TOKEN) {
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

        return Optional.ofNullable(constructor);
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Runtime.RUNTIME.print("run() called on " + toString(), Console.MessageType.OUTPUT);

        getSubBlock(Method.class, "main").get().run();
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
