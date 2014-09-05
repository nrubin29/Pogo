package me.nrubin29.pogo.lang2.block;

public class Property extends RootBlock {

    private String name;

    public Property(String name) {
        super(null);

        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void run() {
        // We don't want to do anything here. Methods will be automatically called.
    }

    @Override
    public String toString() {
        return getClass() + " name=" + name;
    }
}
