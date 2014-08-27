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
            boolean success = true;

            for (int i = 0; i < c.getParameters().length && tokens.hasNextToken(); i++) {
                Parameter p = c.getParameters()[i];
                Token t = tokens.nextToken();

                if (t.getToken().equals(")")) {
                    break;
                }

                if (t.getType() == Token.TokenType.TOKEN) {
                    Value value = Utils.parseToken(t, block);

                    if (!value.getType().isTokenType(t.getType())) {
                        success = false;
                        break;
                    }
                }

                else {
                    if (!p.getMatchedType().isTokenType(t.getType())) {
                        System.err.println("Type mismatch for parameter " + p.getName() + ". Type is " + t.getType() + ". Should be " + p.getMatchedType() + ".");
                        success = false;
                        break;
                    }
                }
            }

            if (success) {
                constructor = c;
                break;
            }
        }

        return Optional.ofNullable(constructor);
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Runtime.RUNTIME.print("run() called on " + toString(), Console.MessageType.OUTPUT);

        getSubBlock(Method.class, "main").get().run();
    }

    public boolean isTokenType(String type) {
        return false;
    }

    @Override
    public String toString() {
        return getClass() + " name=" + name;
    }

    @Override
    public Class clone() {
//        Class clazz = new Class(name);
//        cloneHelp(super.clone());
//        return clazz;

        return this;
    }
}
