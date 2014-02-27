package me.nrubin29.pogo;

import me.nrubin29.pogo.gui.IDE;
import me.nrubin29.pogo.lang.Block;

import javax.swing.*;

public class PogoPlayer {
	
	public static void main(String[] args) throws InvalidCodeException {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable e) {
            	System.out.println("The following stack trace was caught and will be shown to the user:");
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        new IDE();
	}

    public static String implode(String[] strs, Block block) throws InvalidCodeException {
        StringBuilder builder = new StringBuilder();

        for (String str : strs) {
            if (str.startsWith("_") && block != null && block.getVariable(str.substring(1)) != null) {
                builder.append(block.getVariable(str.substring(1)).getValue());
            }
            
            else builder.append(str);

            builder.append(" ");
        }

        return builder.toString().trim();
    }
}