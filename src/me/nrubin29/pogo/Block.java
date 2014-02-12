package me.nrubin29.pogo;

import java.util.ArrayList;

public class Block {

    private Block superBlock;
    private ArrayList<Variable> vars;

    public Block(Block superBlock) {
        this.superBlock = superBlock;
        this.vars = new ArrayList<Variable>();
    }

    public Block getSuperBlock() {
        return superBlock;
    }

    public void addVariable(Variable.VariableType t, String name, Object value) {
        vars.add(new Variable(t, name, value));
    }

    public Variable getVariable(String name) throws InvalidCodeException {
        for (Variable v : vars) {
            if (v.getName().equals(name)) return v;
        }

        throw new InvalidCodeException("Variable " + name + " is not declared.");
    }
}