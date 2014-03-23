package me.nrubin29.pogo;

import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.ide.IDE;

import javax.swing.*;

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

                    if (ide != null && !(e instanceof Utils.IDEException))
                        ide.getConsole().write("Error: " + e, Console.MessageType.ERROR);
                    else JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        if (usingConsole) {
            throw new Utils.ConsoleException("Console support is broken at this time.");

//            String flag = args[0];
//            File file;
//
//            if (flag.equals("-p")) {
//                File projectPath = new File(args[1]);
//                file = new File(projectPath, args[2] + ".pogo");
//                if (!projectPath.exists()) throw new ConsoleException("Could not find project at path.");
//                if (!file.exists()) throw new ConsoleException("Could not find file in project.");
//            } else if (flag.equals("-f")) {
//                file = new File(args[1]);
//                if (!file.exists()) throw new ConsoleException("Could not find file at path.");
//            } else throw new ConsoleException("Invalid flag.");
//
//            try {
//                new me.nrubin29.pogo.lang.Class(Utils.readFile(file)).run(new Utils.Writable() {
//                    @Override
//                    public void write(String text, Console.MessageType messageType) {
//                        if (messageType == Console.MessageType.ERROR) System.err.println(text);
//                        else System.out.println(text);
//                    }
//                });
//            } catch (Exception e) {
//                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
//            }
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