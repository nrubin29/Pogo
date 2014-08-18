package me.nrubin29.pogo.lang2;

public enum Visibility {

    /**
     * The method to which this visibility is applied can be accessed without an instance of the containing class.
     * This is the equivalent of {@code public static} in Java.
     */
    PUBLIC,

    /**
     * The method to which this visibility is applied must be accessed with an instance of the containing class.
     * This is the equivalent of {@code public} in Java.
     */
    INSTANCE,

    /**
     * The method to which this visibility is applied can only be accessed by the containing class.
     * This is the equivalent of {@code private} in Java.
     */
    PRIVATE
}