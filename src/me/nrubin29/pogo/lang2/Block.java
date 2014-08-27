package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.ide.Console;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Block implements Cloneable {

    private Block superBlock;
    private ArrayList<Block> subBlocks;
    private ArrayList<Variable> variables;

    public Block(Block superBlock) {
        this.superBlock = superBlock;
        this.subBlocks = new ArrayList<>();
        this.variables = new ArrayList<>();
    }

    public Block getSuperBlock() {
        return superBlock;
    }

    public Block[] getBlockTree() {
        ArrayList<Block> tree = new ArrayList<>();

        Block b = this;

        while (b != null) {
            tree.add(b);
            b = b.getSuperBlock();
        }

        Collections.reverse(tree);

        return tree.toArray(new Block[tree.size()]);
    }

    public ArrayList<Block> getSubBlocks() {
        return subBlocks;
    }

    public <T extends Block> List<T> getSubBlocks(java.lang.Class<T> clazz) {
        return getSubBlocks().stream()
                .filter(block -> clazz.isAssignableFrom(block.getClass()))
                .map(block -> (T) block)
                .collect(Collectors.toList());
    }

    public <T extends Block> T add(T subBlock) {
        subBlocks.add(subBlock);
        return subBlock;
    }

    public <T extends Block & Nameable> Optional<T> getSubBlock(java.lang.Class<T> clazz, String name) {
        return getSubBlocks(clazz).stream().filter(block -> block.getName().equals(name)).findFirst();
    }

    public <T extends Block & Nameable> boolean hasSubBlock(java.lang.Class<T> clazz, String name) {
        return getSubBlock(clazz, name).isPresent();
    }

    public Optional<Variable> getVariable(String name) {
        Optional<Variable> superBlockVariable = Arrays.stream(getBlockTree(), 0, getBlockTree().length - 1)
                .filter(b -> b.hasVariable(name))
                .map(b -> b.getVariable(name).get())
                .findFirst();

        if (superBlockVariable.isPresent()) {
            return superBlockVariable;
        }

        else {
            return variables.stream().filter(variable -> variable.getName().equals(name)).findFirst();
        }
    }

    public boolean hasVariable(String name) {
        return getVariable(name).isPresent();
    }

    public Variable addVariable(Variable variable) throws InvalidCodeException {
        Runtime.RUNTIME.print("Going to add variable " + variable + " to " + toString(), Console.MessageType.OUTPUT);

        Optional<Variable> v = getVariable(variable.getName());

        if (v.isPresent()) {
            Variable var = v.get();

            if (!var.getType().equals(variable.getType())) {
                throw new InvalidCodeException("Attempted to reassign variable using wrong type.");
            }

            var.setValue(variable.getValue());
        }

        else {
            variables.add(variable);
        }

        return variable;
    }

    public abstract void run() throws InvalidCodeException, IOException;

    public abstract String toString();

    @Override
    public Block clone() {
        Block block = new Block(superBlock) {
            @Override
            public void run() throws InvalidCodeException, IOException {
                Block.this.run();
            }

            @Override
            public String toString() {
                return Block.this.toString();
            }
        };

        block.subBlocks = subBlocks;
        block.variables = variables;

        return block;
    }

    protected void cloneHelp(Block clonedBlock) {
        this.subBlocks = clonedBlock.subBlocks;
        this.variables = clonedBlock.variables;
    }
}