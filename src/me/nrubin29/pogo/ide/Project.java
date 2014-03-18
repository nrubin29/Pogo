package me.nrubin29.pogo.ide;

import me.nrubin29.pogo.IDEException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

class Project {

    private final File pogoProj;

    public Project(File pogoProj) {
        this.pogoProj = pogoProj;
    }

    public String getName() {
        return pogoProj.getName();
    }

    public File[] getFiles() {
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(pogoProj.listFiles()));

        for (File file : files) {
            if (!file.getName().endsWith(".pogo")) files.remove(file);
        }

        return files.toArray(new File[files.size()]);
    }

    public File getFile(String name) throws IDEException {
        File file = new File(pogoProj, name + ".pogo");

        if (!file.exists()) throw new IDEException("Attempted to get non-existent file.");

        return file;

//		for (File file : files) {
//			if (file.getName().equals(name)) return file;
//		}
//		
//		return null;
    }

    public File addFile(String name) {
        try {
            File f = new File(pogoProj, name + ".pogo");
            f.createNewFile();
            return f;
        } catch (Exception e) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), new IDEException("Could not create file."));
            return null;
        }
    }

    public void deleteFile(String name) throws IDEException {
        if (!new File(pogoProj, name + ".pogo").delete()) throw new IDEException("Could not delete file.");
    }
}