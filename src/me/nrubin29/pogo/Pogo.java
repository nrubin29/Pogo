package me.nrubin29.pogo;

import me.nrubin29.pogo.Utils.ConsoleException;
import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.ide.IDE;
import me.nrubin29.pogo.ide.Instance;
import me.nrubin29.pogo.ide.Project;

import javax.swing.*;
import java.io.File;

class Pogo {

    private static IDE ide;

    public static void main(String[] args) throws Utils.ConsoleException {
        final boolean usingConsole = args.length > 0;

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                if (usingConsole) {
                    System.err.println("Error: " + e);
                } else {
                    System.out.println("The following stack trace was caught and will be shown to the user:");
                    e.printStackTrace();

                    if (ide != null && !(e instanceof Utils.IDEException)) {
                        ide.getConsole().write("Error: " + e, Console.MessageType.ERROR);
                    } else JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        if (usingConsole) {
            File projectPath = new File(args[0]);
            if (!projectPath.exists()) throw new ConsoleException("Could not find project at path.");

            try {
                Instance.createInstance(new Project(projectPath), new Utils.Writable() {
                    @Override
                    public void write(String text, Console.MessageType messageType) {
                        if (messageType == Console.MessageType.ERROR) System.err.println(text);
                        else System.out.println(text);
                    }
                });
            } catch (Exception e) {
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
            }
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ide = new IDE();
                }
            });
        }
    }
}