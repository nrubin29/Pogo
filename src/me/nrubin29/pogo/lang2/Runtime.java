package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.Pogo;
import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.ide.Project;
import me.nrubin29.pogo.lang2.parser.*;

import java.io.IOException;
import java.util.Scanner;

public class Runtime {

    public static Runtime RUNTIME;

    private final Project project;
    private final Class[] classes;

    private Runtime(Project project) {
        this.project = project;
        this.classes = new Class[project.getFiles().size()];
    }

    public static void start(Project project) throws InvalidCodeException, IOException {
        RUNTIME = new Runtime(project);
        RUNTIME.run();
    }

    private void run() throws InvalidCodeException, IOException {
        Parser[] parsers = {
                new ClassParser(),
                new ConstructorParser(),
                new ForParser(),
                new IfParser(),
                new MethodInvocationParser(),
                new MethodParser(),
                new ReturnParser(),
                new VariableDeclarationParser(),
                new WhileParser()
        };

        Class mainClass = null;

        for (int i = 0; i < classes.length; i++) {
            Block block = null;

            for (String line : Utils.readFile(project.getFiles().get(i), false)) {
                line = line.trim();
                PogoTokenizer tokenizer = new PogoTokenizer(line);

                String firstToken = tokenizer.nextToken().getToken();
                tokenizer.pushBack();

                if (firstToken == null) {
                    continue;
                }

                if (firstToken.equals("end")) {
                    if (block == null) {
                        throw new InvalidCodeException("Attempted to end non-existent block.");
                    }

                    if (!(block instanceof Endable)) {
                        throw new InvalidCodeException("Attempted to end non-endable block.");
                    }

                    block = block.getSuperBlock();

                    continue;
                }

                boolean success = false;

                for (Parser parser : parsers) {
                    if (parser.shouldParseLine(line)) {
                        Block newBlock = parser.parse(block, tokenizer);

                        if (block == null) {
                            if (!(newBlock instanceof Class)) {
                                throw new InvalidCodeException("File does not begin with class declaration.");
                            }
                        }

                        else {
                            if (newBlock instanceof Method || newBlock instanceof Constructor) { // If it is a method or constructor, we add the method to the class.
                                block.getBlockTree()[0].add(newBlock);
                            }

                            else {
                                block.add(newBlock);
                            }
                        }

                        if (!(newBlock instanceof ReadOnlyBlock)) { // If it is a read only block, we don't want that to be the new superblock.
                            block = newBlock;
                        }

                        success = true;
                        break;
                    }
                }

                if (!success) {
                    throw new InvalidCodeException("Could not parse line " + line);
                }
            }

            if (block != null) {
                classes[i] = (Class) block.getBlockTree()[0];
            }

            else {
                throw new InvalidCodeException("Empty file.");
            }

            if (classes[i].hasSubBlock(Method.class, "main")) {
                mainClass = classes[i];
            }
        }

        if (mainClass == null) {
            throw new InvalidCodeException("No main method in project.");
        }

        mainClass.run();
    }

    public Class getPogoClass(String name) {
        for (Class c : classes) {
            if (c.getName().equals(name)) {
                return c;
            }
        }

        return null;
    }

    public void print(String msg, Console.MessageType type) {
        if (Pogo.getIDE() != null) {
            Pogo.getIDE().getConsole().write(msg, type);
        }

        else {
            if (type == Console.MessageType.ERROR) {
                System.err.println(msg);
            }

            else {
                System.out.println(msg);
            }
        }
    }

    public String prompt() {
        if (Pogo.getIDE() != null) {
            return Pogo.getIDE().getConsole().prompt();
        }

        else {
            return new Scanner(System.in).nextLine();
        }
    }
}