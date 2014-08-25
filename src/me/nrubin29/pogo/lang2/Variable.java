package me.nrubin29.pogo.lang2;

public class Variable extends Value implements Nameable {

    private Block block;
    private String name;

    public Variable(Block block, String name) {
        this(block, name, null, null);
    }

    public Variable(Block block, String name, Type type) {
        this(block, name, type, null);
    }

    public Variable(Block block, String name, Type type, Object value) {
        super(type, value);

        this.block = block;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public String toString() {
        return getClass() + " name=" + name + " type=" + getType() + " value=" + getValue();
    }
}