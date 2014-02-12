package me.nrubin29.pogo;

public class InvalidCodeException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidCodeException(String msg) {
		super(msg);
	}
}