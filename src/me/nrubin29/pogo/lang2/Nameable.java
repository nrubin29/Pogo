package me.nrubin29.pogo.lang2;

public interface Nameable {

    public String getName();

    /*
    If a class implements Nameable, it needs to have a toString() that includes "name=" + name.
     */
    public String toString();
}