package me.nrubin29.pogo.ide;

import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.Utils.Writable;
import me.nrubin29.pogo.lang.Class;

import java.io.File;

public class Instance {

    public static Instance CURRENT_INSTANCE;
    private final Writable writable;
    private final Project project;
    private final Class[] classes;

    private Instance(Project project, Writable writable) {
        this.writable = writable;
        this.project = project;
        this.classes = new Class[project.getFiles().length];
    }

    public static void createInstance(Project project, Utils.Writable writable) throws Utils.InvalidCodeException {
        CURRENT_INSTANCE = new Instance(project, writable);
        CURRENT_INSTANCE.run();
    }

    public Writable getWritable() {
        return writable;
    }

    private void run() throws Utils.InvalidCodeException {
        Class mainClass = null;

        for (int i = 0; i < classes.length; i++) {
            File f = project.getFiles()[i];

            me.nrubin29.pogo.lang.Class c = new Class(Utils.readFile(f));
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
            if (c.name().equals(name)) return c;
        }

        return null;
    }
}