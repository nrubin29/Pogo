package me.nrubin29.pogo.ide;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Project {

    private final File pogoProj;

    public Project(File pogoProj) {
        this.pogoProj = pogoProj;
    }

    public String getName() {
        return pogoProj.getName();
    }

    public List<File> getFiles() {
        return Arrays.stream(pogoProj.listFiles()).filter(file -> file.getName().endsWith(".pogo")).collect(Collectors.toList());
    }

    public File getFile(String name) {
        File file = new File(pogoProj, name + ".pogo");

        if (!file.exists()) {
            throw new IDEException("File does not exist.");
        }

        return file;
    }

    public File addFile(String name) {
        try {
            File f = new File(pogoProj, name + ".pogo");
            f.createNewFile();
            return f;
        } catch (Exception e) {
            throw new IDEException("Could not create file.");
        }
    }

    public void deleteFile(String name) {
        if (!new File(pogoProj, name + ".pogo").delete()) {
            throw new IDEException("Could not delete file.");
        }
    }
}