package me.nrubin29.pogo;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JOptionPane;

/*
 * TODO: Not necessarily in this order:
 * Modifying variable values.
 * Not setting value of variable on declaration.
 * Getting input.
 * For loop.
 * While loop.
 * Arrays.
 * Foreach loop.
 * If statement.
 * More variable types.
 * Math.
 * Invoke other methods.
 * Class-level variables.
 * Switch to block format (for class, method, if, loop, etc.)
 */
public class PogoPlayer {
	
	private GUI gui;
	
	/*
	 * This is sample code. I will add a file chooser once I get a few more features added.
	 */
	private String code =
			"// Sample Pogo Code." + "\n" +
			"\n" +
			"method main // Declaring the main method. This will be called when the code starts." + "\n" +
			"declare string str = Hello // Declaring a string called str and setting it equal to Hello." + "\n" +
			"print _str ~World // Print the contents of str (which is Hello), a space, and the word World." + "\n" +
			"end // End the main method.";

	public PogoPlayer() throws InvalidCodeException {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread thread, Throwable e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		});
		
		gui = new GUI();
		
		new Class(gui, code);
	}
	
	public static void main(String[] args) throws InvalidCodeException {
		new PogoPlayer();
	}
}