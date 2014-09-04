package me.nrubin29.pogo.lang2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class VariableDeclaration extends ReadOnlyBlock implements Nameable {

    private Token typeToken, nameToken;
    private boolean init;
    private PogoTokenizer tokenizer;

    public VariableDeclaration(Block superBlock, Token typeToken, Token nameToken, boolean init, PogoTokenizer tokenizer, Token... propertyTokens) {
        super(superBlock, propertyTokens);

        this.typeToken = typeToken;
        this.nameToken = nameToken;
        this.init = init;
        this.tokenizer = tokenizer;
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Type type = Type.match(typeToken.getToken());

        if (type == null) {
            throw new InvalidCodeException("Expected type, got " + typeToken + ".");
        }

        if (type == PrimitiveType.VOID) {
            throw new InvalidCodeException("Attempted to instantiate variable with type void.");
        }

        Variable variable = new Variable(getSuperBlock(), nameToken.getToken(), type);

        if (init) {
            Token firstToken = tokenizer.nextToken();

            if (firstToken.getToken().equals("new")) {
                if (!tokenizer.nextToken().getToken().equals("(")) {
                    throw new InvalidCodeException("Variable declaration does not contain opening parenthesis.");
                }

                if (type instanceof PrimitiveType) {
                    throw new InvalidCodeException("Attempted to instantiate primitive type with new.");
                }

                else {
                    Class clazz = ((Class) type).clone();
                    Optional<Constructor> c = clazz.getConstructor(tokenizer.clone(), getSuperBlock());

                    if (c.isPresent()) {
                        ArrayList<Value> values = new ArrayList<>();

                        while (tokenizer.hasNextToken()) {
                            Token token = tokenizer.nextToken();

                            if (token.getToken().equals(",")) {
                                continue;
                            }

                            else if (token.getToken().equals(")")) {
                                break;
                            }

                            else {
                                values.add(Utils.parseToken(token, getSuperBlock()));
                            }
                        }

                        c.get().invoke(values);
                    }

                    else {
                        throw new InvalidCodeException("Could not find constructor for given parameters.");
                    }

                    variable.setValue(clazz);
                }
            }

            else {
                variable.setValue(Utils.parseToken(firstToken, getSuperBlock()).getValue());
            }
        }

        for (Token token : getPropertyTokens()) {
            Property property = Runtime.RUNTIME.getPogoClass(token.getToken().substring(token.getToken().indexOf('@') + 1));

            if (property == null) {
                throw new InvalidCodeException("Expected property, found " + token.getToken().substring(token.getToken().indexOf('@') + 1) + ".");
            }

            addProperty(property);

            Optional<Method> method = property.getMethod("applyToVariable");

            if (!method.isPresent()) {
                throw new InvalidCodeException("Property is not applicable to variables.");
            }

            method.get().run();
        }

        getSuperBlock().addVariable(variable);
    }

    @Override
    public String getName() {
        return nameToken.getToken();
    }

    public String getType() {
        return typeToken.getToken();
    }

    @Override
    public String toString() {
        return getClass() + " typeToken=" + typeToken + " nameToken=" + nameToken + " init=" + init;
    }
}