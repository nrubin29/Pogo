package me.nrubin29.pogo;

import javax.swing.*;
import java.lang.Thread.UncaughtExceptionHandler;

/*
 * TODO: Not necessarily in this order:
 * For loop.
 * While loop.
 * Arrays.
 * Foreach loop.
 * If statement.
 * More variable types.
 * Math.
 * Invoke other methods.
 * Scope (use class-level variables in subblocks).
 */
public class PogoPlayer {
	
	private GUI gui;
	
	/*
	This is sample code. It contains variable declaration and prints Hello World.
	 */
	private String code1 =
			"// Sample Pogo Code." + "\n" +
			"method main // Declaring the main method. This will be called when the code starts." + "\n" +
			"declare string str = Hello // Declaring a string called str and setting it equal to Hello." + "\n" +
            "declare string str1 // Declaring a null string called str1." + "\n" +
            "set str1 World // Setting str1 to Hello." + "\n" +
			"print _str _str1 // Print the contents of str (which is Hello) and the contents of str1 (which is World)." + "\n" +
			"end // End the main method.";

    /*
    This is sample code. It gets the user's input.
     */
    private String code2 =
            "// Sample Pogo Code." + "\n" +
            "method main" + "\n" +
            "declare string name" + "\n" +
            "print Enter your name." + "\n" +
            "getinput name" + "\n" +
            "print Hello there, _name !" + "\n" +
            "end";

	public PogoPlayer() throws InvalidCodeException {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread thread, Throwable e) {
                e.printStackTrace();
                if (gui != null) gui.write(e.getMessage());
				else JOptionPane.showMessageDialog(null, e.getMessage());
			}
		});
		
		gui = new GUI();
		
		new Class(gui, code2);
	}
	
	public static void main(String[] args) throws InvalidCodeException {
		new PogoPlayer();
	}
}