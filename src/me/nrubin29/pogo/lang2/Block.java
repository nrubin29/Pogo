package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.ide.Console;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Block implements Cloneable {

    private Block superBlock;
    private ArrayList<Block> subBlocks;
    private ArrayList<Property> properties;
    private ArrayList<Variable> variables;

    private ArrayList<Token> propertyTokens;

    public Block(Block superBlock, Token... propertyTokens) {
        this.superBlock = superBlock;
        this.subBlocks = new ArrayList<>();
        this.properties = new ArrayList<>();
        this.variables = new ArrayList<>();

        this.propertyTokens = new ArrayList<>(Arrays.asList(propertyTokens));
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

    @SuppressWarnings("unchecked")
    public <T extends Block> List<T> getSubBlocks(java.lang.Class<T> clazz) {
        return getSubBlocks().stream()
                .filter(block -> clazz.isAssignableFrom(block.getClass()))
                .map(block -> (T) block)
                .collect(Collectors.toList());
    }

    public <T extends Block> void add(T subBlock) {
        subBlocks.add(subBlock);
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

    public void addVariable(Variable variable) throws InvalidCodeException {
        Runtime.RUNTIME.print("Going to add variable " + variable.getName() + ".", Console.MessageType.OUTPUT);

        Optional<Variable> v = getVariable(variable.getName());

        if (v.isPresent()) {
            throw new InvalidCodeException("Variable " + variable.getName() + " is already defined in this scope. " + Arrays.toString(getBlockTree()));
        }

        else {
            variables.add(variable);
        }
    }

    public void removeVariable(Variable variable) throws InvalidCodeException {
        Optional<Block> superBlock = Arrays.stream(getBlockTree(), 0, getBlockTree().length - 1)
                .filter(b -> b.hasVariable(variable.getName()))
                .findFirst();

        if (superBlock.isPresent()) {
            superBlock.ifPresent(block -> block.variables.remove(variable));
        }

        else {
            if (hasVariable(variable.getName())) {
                variables.remove(variable);
            }

            else {
                throw new InvalidCodeException("Variable " + variable.getName() + " cannot be removed because it is not defined in this scope.");
            }
        }
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public Token[] getPropertyTokens() {
        return propertyTokens.toArray(new Token[propertyTokens.size()]);
    }

    public abstract void run() throws InvalidCodeException, IOException;

    public abstract String toString();

    @Override
    @SuppressWarnings({"CloneDoesntCallSuperClone", "CloneDoesntDeclareCloneNotSupportedException"})
    public Block clone() {
        return this;
    }
}