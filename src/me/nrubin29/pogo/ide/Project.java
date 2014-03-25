package me.nrubin29.pogo.ide;

import me.nrubin29.pogo.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Project {

    private final File pogoProj;

    public Project(File pogoProj) {
        this.pogoProj = pogoProj;
    }

    public String getName() {
        return pogoProj.getName();
    }

    public File[] getFiles() {
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(pogoProj.listFiles())), removeFiles = new ArrayList<File>();

        for (File file : files) {
            if (!file.getName().endsWith(".pogo")) removeFiles.add(file);
        }

        files.removeAll(removeFiles);

        return files.toArray(new File[files.size()]);
    }

    public File getFile(String name) throws Utils.IDEException {
        File file = new File(pogoProj, name + ".pogo");

        if (!file.exists()) throw new Utils.IDEException("Attempted to get non-existent file.");

        return file;
    }

    public File addFile(String name) {
        try {
            File f = new File(pogoProj, name + ".pogo");
            f.createNewFile();
            return f;
        } catch (Exception e) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), new Utils.IDEException("Could not create file."));
            return null;
        }
    }

    public void deleteFile(String name) throws Utils.IDEException {
        if (!new File(pogoProj, name + ".pogo").delete()) throw new Utils.IDEException("Could not delete file.");
    }
}