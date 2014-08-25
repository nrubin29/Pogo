package me.nrubin29.pogo;

import javafx.application.Application;
import javafx.stage.Stage;
import me.nrubin29.pogo.ide.IDE;
import me.nrubin29.pogo.lang2.InvalidCodeException;

public class Pogo extends Application {

    private static IDE ide;

    public void start(Stage stage) {
        (ide = new IDE()).start(stage);
    }

    public static IDE getIDE() {
        return ide;
    }

//    public Pogo() {
//        final boolean usingConsole = args.length > 0;
//
//        Thread.setDefaultUncaughtExceptionHandler((thread, e) -> {
//            if (usingConsole) {
//                System.err.println("Error: " + e);
//            }
//
//            else {
//                System.out.println("The following stack trace was caught and will be shown to the user:");
//                e.printStackTrace();
//
//                if (ide != null && !(e instanceof Utils.IDEException)) {
//                    ide.getConsole().write("Error: " + e, Console.MessageType.ERROR);
//                }
//
//                else {
//                    JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
//
//        if (usingConsole) {
//            File projectPath = new File(args[0]);
//            if (!projectPath.exists()) {
//                throw new Utils.ConsoleException("Could not find project at path.");
//            }
//
//            try {
//                Instance.createInstance(new Project(projectPath), new Utils.Writable() {
//                    @Override
//                    public void write(String text, Console.MessageType messageType) {
//                        if (messageType == Console.MessageType.ERROR) System.err.println(text);
//                        else System.out.println(text);
//                    }
//                });
//            } catch (Exception e) {
//                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
//            }
//        } else {
//            launch(args);
//        }
//    }

    public static void main(String[] args) throws InvalidCodeException {
        launch(args);
    }
}