package me.nrubin29.pogo;

import me.nrubin29.pogo.gui.IDE;

import javax.swing.*;
import java.lang.Thread.UncaughtExceptionHandler;

/*
 * Arrays:
    * Foreach loop.
 * Booleans:
    * For loop.
    * While loop.
 * Features:
    * Math.
 */
public class PogoPlayer {

	public PogoPlayer() throws InvalidCodeException {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        new IDE();
	}
	
	public static void main(String[] args) throws InvalidCodeException {
		new PogoPlayer();
	}
}