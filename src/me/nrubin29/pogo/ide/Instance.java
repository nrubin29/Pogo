package me.nrubin29.pogo.ide;

import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.lang.Class;

import java.io.File;

public class Instance {

    public static Instance CURRENT_INSTANCE;
    private final Project project;
    private final Class[] classes;

    private Instance(Project project) {
        this.project = project;
        this.classes = new Class[project.getFiles().length];
    }

    public static void createInstance(Project project, Utils.Writable writable) throws Utils.InvalidCodeException {
        CURRENT_INSTANCE = new Instance(project);
        CURRENT_INSTANCE.run(writable);
    }

    private void run(Utils.Writable writable) throws Utils.InvalidCodeException {
        Class mainClass = null;

        for (int i = 0; i < classes.length; i++) {
            File f = project.getFiles()[i];

            me.nrubin29.pogo.lang.Class c = new Class(Utils.readFile(f), writable);
            classes[i] = c;
            c.parseClass();

            if (c.hasMain()) mainClass = c;
        }

        if (mainClass == null) throw new Utils.InvalidCodeException("No main method in project.");

        mainClass.getMethod("main").run();
        writable.write("--Terminated.", Console.MessageType.OUTPUT);
    }

    public Class getPogoClass(String name) {
        for (Class c : classes) {
            if (c.getName().equals(name)) return c;
        }

        return null;
    }
}