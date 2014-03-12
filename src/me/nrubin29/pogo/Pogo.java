package me.nrubin29.pogo;

import me.nrubin29.pogo.ide.IDE;

import javax.swing.*;

class Pogo {

    private static IDE ide;

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                System.out.println("The following stack trace was caught and will be shown to the user:");
                e.printStackTrace();
                JOptionPane.showMessageDialog(ide, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ide = new IDE();
            }
        });
    }
}