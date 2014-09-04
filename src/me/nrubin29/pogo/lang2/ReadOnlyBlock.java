package me.nrubin29.pogo.lang2;

import java.util.Optional;

/**
 * Represents a block that cannot have subblocks nor variables.
 */
public abstract class ReadOnlyBlock extends Block {

    public ReadOnlyBlock(Block superBlock, Token... propertyTokens) {
        super(superBlock, propertyTokens);
    }

    @Override
    public <T extends Block> void add(T subBlock) {
        throw new UnsupportedOperationException("Read-only block cannot have subblocks.");
    }

//    @Override
//    public <T extends Block & Nameable> Optional<T> getSubBlock(java.lang.Class<T> clazz, String name) {
//        throw new UnsupportedOperationException("Read-only block cannot have subblocks.");
//    }
//
//    @Override
//    public <T extends Block & Nameable> boolean hasSubBlock(java.lang.Class<T> clazz, String name) {
//        throw new UnsupportedOperationException("Read-only block cannot have subblocks.");
//    }

    @Override
    public Optional<Variable> getVariable(String name) {
        throw new UnsupportedOperationException("Read-only block cannot have variables");
    }

    public boolean hasVariable(String name) {
        throw new UnsupportedOperationException("Read-only block cannot have variables");
    }

    public void addVariable(Variable variable) {
        throw new UnsupportedOperationException("Read-only block cannot have variables");
    }
}