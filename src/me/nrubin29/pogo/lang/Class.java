package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.lang.Method.Visibility;
import me.nrubin29.pogo.lang.Utils.InvalidCodeException;
import me.nrubin29.pogo.lang.Variable.VariableType;
import me.nrubin29.pogo.lang.systemmethod.MethodParser;

import java.util.ArrayList;
import java.util.Arrays;

public class Class extends Block implements VariableType {

    private final String[] code;
    MethodParser methodParser;
    private String name;
    private ArrayList<Method> methods;

    public Class(String[] code) {
        super(null);

        this.code = code;
    }

    public void parseClass() throws Utils.InvalidCodeException {
        this.methods = new ArrayList<Method>();
        this.methodParser = new MethodParser();

        Method currentMethod = null;

        for (String line : code) {
            line = trimComments(line);

            if (line.startsWith("class ")) {
                if (currentMethod != null) {
                    methods.add(currentMethod);
                    currentMethod.parse();
                    currentMethod = null;
                }

                String[] args = line.split(" ");
                this.name = args[1];
                if (args.length > 2) {
                    if (args[2].equals("is")) {
                        setSuperBlock(IDEInstance.CURRENT_INSTANCE.getPogoClass(args[3]));
                    }
                }
            } else if (line.startsWith("method ")) {
                if (currentMethod != null) {
                    methods.add(currentMethod);
                    currentMethod.parse();
                }

                String[] args = line.split(" ");

                if (args.length < 4) {
                    throw new Utils.InvalidCodeException("Missing arguments in method declaration: " + line + ".");
                }

                Visibility vis = Visibility.match(args[1]);
                VariableType returnType = VariableType.VariableTypeMatcher.match(args[2]);
                String[] params = Arrays.copyOfRange(args, 4, args.length);
                currentMethod = new Method(this, vis, args[3], returnType, params);
            } else if (line.startsWith("declare")) {
                if (currentMethod != null) {
                    methods.add(currentMethod);
                    currentMethod.parse();
                    currentMethod = null;
                }

                methodParser.parse(this, line);
            } else {
                if (currentMethod != null && !line.equals("") && !line.equals(" ")) {
                    currentMethod.addLine(line);
                }
            }
        }

        if (name == null) {
            throw new Utils.InvalidCodeException("Did not specify name for class.");
        }
    }

    private String trimComments(String str) {
        StringBuilder fin = new StringBuilder();

        for (String word : str.split(" ")) {
            if (word.startsWith("//")) return fin.toString().trim();
            else fin.append(word).append(" ");
        }

        return fin.toString().trim();
    }

    @Override
    public String name() {
        return name;
    }

    public boolean hasMain() {
        try {
            getMethod("main");
            return true;
        } catch (Utils.InvalidCodeException e) {
            return false;
        }
    }

    public Method getMethod(String name) throws Utils.InvalidCodeException {
        for (Method m : methods) {
            if (m.getName().equals(name)) return m;
        }

        throw new Utils.InvalidCodeException("Method " + name + " does not exist in " + name() + ".");
    }

    @Override
    public void validateValue(Object value, Block block) throws InvalidCodeException {
        if (!value.toString().equals("new"))
            throw new InvalidCodeException("Attempted to instantiate class without using \"new\" value");
    }

    @Override
    public Object formatValue(Object value) throws InvalidCodeException {
        if (!value.toString().equals("new"))
            throw new InvalidCodeException("Attempted to instantiate class without using \"new\" value");
        return value;
    }

    @Override
    public String toString() {
        return "Class methods=" + Arrays.toString(methods.toArray()) + " code=" + Arrays.toString(code);
    }
}