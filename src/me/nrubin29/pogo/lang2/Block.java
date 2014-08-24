package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.Pogo;
import me.nrubin29.pogo.ide.Console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

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
        return (ArrayList<Block>) subBlocks.clone();
    }

    public void add(Block subBlock) {
        subBlocks.add(subBlock);
    }

    public <T extends Block & Nameable> Optional<T> getSubBlock(java.lang.Class<T> clazz, String name) {
        return subBlocks.stream()
                .filter(block -> clazz.isAssignableFrom(block.getClass()))
                .filter(block -> ((Nameable) block).getName().equals(name))
                .map(block -> (T) block)
                .findFirst();
    }

    public <T extends Block & Nameable> boolean hasSubBlock(java.lang.Class<T> clazz, String name) {
        return getSubBlock(clazz, name) != null;
    }

    public Optional<Variable> getVariable(String name) {
        return variables.stream().filter(variable -> variable.getName().equals(name)).findFirst();
    }

    public boolean hasVariable(String name) {
        return getVariable(name).isPresent();
    }

    public void addVariable(Variable variable) throws InvalidCodeException {
        Pogo.getIDE().getConsole().write("Going to add variable " + variable + " to " + toString(), Console.MessageType.OUTPUT);

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