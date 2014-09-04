package me.nrubin29.pogo.lang2;

import java.util.Optional;

/**
 * Represents a root block that can contain methods. Current implementations are {@link me.nrubin29.pogo.lang2.Class} and {@link me.nrubin29.pogo.lang2.Property}
 */
public abstract class RootBlock extends Block implements Nameable {

    public RootBlock(Block superBlock, Token... rootTokens) {
        super(superBlock, rootTokens);
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
                            if (!paramTypes[i].equalsType(method.getParameters()[i].getMatchedType())) {
                                throw new InvalidCodeException("Type mismatch for parameter " + method.getParameters()[i].getName() + ". Type is " + paramTypes[i] + ". Should be " + method.getParameters()[i].getMatchedType() + ".");
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
}