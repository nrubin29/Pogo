package me.nrubin29.pogo.lang2;

public class InvalidCodeException extends Exception {

    public InvalidCodeException(String message) {
        super(message);
    }

    public InvalidCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}