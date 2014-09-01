package me.nrubin29.pogo.lang2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class VariableReassignment extends ReadOnlyBlock implements Nameable {

    private Token nameToken;
    private PogoTokenizer tokenizer;

    public VariableReassignment(Block superBlock, Token nameToken, PogoTokenizer tokenizer) {
        super(superBlock);

        this.nameToken = nameToken;
        this.tokenizer = tokenizer;
    }

    @Override
    public void run() throws InvalidCodeException, IOException {
        Optional<Variable> v = getVariable(nameToken.getToken());

        if (!v.isPresent()) {
            throw new InvalidCodeException("Variable " + nameToken.getToken() + " could not be reassigned because it is not defined in this scope.");
        }

        else {
            Variable variable = v.get();

            Token firstToken = tokenizer.nextToken();

            if (firstToken.getToken().equals("new")) {
                if (!tokenizer.nextToken().getToken().equals("(")) {
                    throw new InvalidCodeException("Variable declaration does not contain opening parenthesis.");
                }

                if (variable.getType() instanceof PrimitiveType) {
                    throw new InvalidCodeException("Attempted to instantiate primitive type with new.");
                }

                else {
                    Class clazz = ((Class) variable.getType()).clone();
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
    }

    @Override
    public String getName() {
        return nameToken.getToken();
    }

    @Override
    public String toString() {
        return getClass() + " nameToken=" + nameToken;
    }
}