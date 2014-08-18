package me.nrubin29.pogo.lang2;

public class Class extends Block implements Type, Nameable, Cloneable {

    private String name;

    public Class(Block superBlock, String name) {
        super(superBlock);

        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void run() {
        System.out.println("run() called on " + toString());

        getSubBlock(Method.class, "main").get().run();
    }

    @Override
    public String toString() {
        return getClass() + " name=" + name;
    }

    @Override
    public Class clone() {
        Class clazz = new Class(getSuperBlock(), name);
        cloneHelp(super.clone());
        return clazz;
    }
}
