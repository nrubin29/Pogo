package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.ide.Project;
import me.nrubin29.pogo.lang2.parser.*;

import java.io.IOException;
import java.io.StreamTokenizer;

public class IDEInstance {

    public static IDEInstance CURRENT_INSTANCE;

    private final Project project;
    private final Class[] classes;

    private IDEInstance(Project project) {
        this.project = project;
        this.classes = new Class[project.getFiles().size()];
    }

    public static void createInstance(Project project) throws InvalidCodeException, IOException {
        CURRENT_INSTANCE = new IDEInstance(project);
        CURRENT_INSTANCE.run();
    }

    private void run() throws InvalidCodeException, IOException {
        Parser[] parsers = { new ClassParser(), new MethodParser(), new VariableParser(), new IfParser() };
        Class mainClass = null;

        for (int i = 0; i < classes.length; i++) {
            Block block = null;

            for (String line : Utils.readFile(project.getFiles().get(i), false)) {
                StreamTokenizer tokenizer = Utils.tokenize(line.trim());

                tokenizer.nextToken();
                String firstToken = tokenizer.sval;
                tokenizer.pushBack();

                if (firstToken == null) {
                    continue;
                }

                if (firstToken.equals("end")) { // This could be its own Parser, but it probably shouldn't be.
                    if (block == null) {
                        throw new InvalidCodeException("Attempted to end non-existent block.");
                    }

                    if (!(block instanceof ConditionalBlock)) {
                        throw new InvalidCodeException("Attempted to end non-conditional block.");
                    }

                    block = block.getSuperBlock();
                }

                for (Parser parser : parsers) {
                    if (parser.shouldParse(firstToken)) {
                        Block newBlock = parser.parse(block, tokenizer);

                        if (newBlock != null) {
                            if (block != null) {
                                if (newBlock instanceof Method) { // If it is a method, we add the method to the class.
                                    block.getBlockTree()[0].add(newBlock);
                                } else {
                                    block.add(newBlock);
                                }
                            }

                            block = newBlock;
                        }

                        break;
                    }
                }
            }

            if (block != null) {
                classes[i] = (Class) block.getBlockTree()[0];
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
}