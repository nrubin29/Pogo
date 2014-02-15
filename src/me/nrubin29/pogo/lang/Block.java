package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public abstract class Block {

    private final Block superBlock;
    private final ArrayList<Variable> vars;

    Block(Block superBlock) {
        this.superBlock = superBlock;
        this.vars = new ArrayList<Variable>();
    }

    Block getSuperBlock() {
        return superBlock;
    }

    public Block[] getBlockTree() {
        ArrayList<Block> tree = new ArrayList<Block>();

        Block b = this;

        while (b != null) {
            tree.add(b);
            b = b.getSuperBlock();
        }

        Collections.reverse(tree);

        return tree.toArray(new Block[tree.size()]);
    }

    public void addVariable(Variable.VariableType t, String name, Object value) {
        vars.add(new Variable(t, name, value));
    }

    public Variable getVariable(String name) throws InvalidCodeException {
        for (Block b : Arrays.copyOfRange(getBlockTree(), 0, getBlockTree().length - 1)) {
            if (b.hasVariable(name)) return b.getVariable(name);
        }

        for (Variable v : vars) {
            if (v.getName().equals(name)) return v;
        }

        throw new InvalidCodeException("Variable " + name + " is not declared.");
    }

    boolean hasVariable(String name) {
        for (Variable v : vars) {
            if (v.getName().equals(name)) return true;
        }

        return false;
    }

    protected abstract void run() throws InvalidCodeException;
}