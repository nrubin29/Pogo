package me.nrubin29.pogo;

import me.nrubin29.pogo.gui.IDE;

import javax.swing.*;
import java.lang.Thread.UncaughtExceptionHandler;

class PogoPlayer {

	private PogoPlayer() {
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