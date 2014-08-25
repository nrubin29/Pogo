package me.nrubin29.pogo.lang2;

public class Else extends Block implements Endable {

    public Else(Block superBlock) {
        super(superBlock);
    }

    @Override
    public void run() {
        // We don't want to do anything because the If will run this.
    }

    @Override
    public String toString() {
        return String.valueOf(getClass());
    }
}