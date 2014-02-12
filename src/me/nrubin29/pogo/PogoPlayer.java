package me.nrubin29.pogo;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

/*
 * Arrays:
    * Foreach loop.
 * Booleans:
    * If statements.
    * For loop.
    * While loop.
 * Features:
    * Math.
 */
public class PogoPlayer {
	
	private GUI gui;

	public PogoPlayer() throws InvalidCodeException {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread thread, Throwable e) {
                e.printStackTrace();
                if (gui != null) gui.write(!(e instanceof InvalidCodeException) ? e.getClass().getSimpleName() + " " : "" + e.getMessage());
				else JOptionPane.showMessageDialog(null, e.getMessage());
			}
		});

        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Pogo Code", "pogo"));
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setMultiSelectionEnabled(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                ArrayList<String> in = new ArrayList<String>();
                BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()));

                while (reader.ready()) in.add(reader.readLine());

                reader.close();

                gui = new GUI();

                new Class(gui, in);
            }

            catch (Exception e) { }
        }

        else System.exit(0);
	}
	
	public static void main(String[] args) throws InvalidCodeException {
		new PogoPlayer();
	}
}