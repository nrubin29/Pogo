package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.lang.Variable.VariableType;
import me.nrubin29.pogo.lang.function.MethodParser;

import java.util.ArrayList;
import java.util.Arrays;

public class Class extends Block {

    private final String[] code;
    private final Utils.Writable writable;
    MethodParser methodParser;
    private String name;
    private ArrayList<Method> methods;

    public Class(String[] code, Utils.Writable writable) {
        super(null);

        this.code = code;
        this.writable = writable;
    }

    public void parseClass() throws Utils.InvalidCodeException {
        this.methods = new ArrayList<Method>();
        this.methodParser = new MethodParser(writable);

        Method currentMethod = null;

        for (String line : code) {
            line = trimComments(line);

            if (line.startsWith("class ")) {
                this.name = line.split(" ")[1];
            } else if (line.startsWith("method ")) {
                String[] args = line.split(" ");
                String[] mArgs = args[1].split(":");
                String methodName = mArgs[0];

                if (mArgs.length == 1) {
                    throw new Utils.InvalidCodeException("Did not specify return type for method " + methodName + ".");
                }

                VariableType returnType = VariableType.match(mArgs[1]);
                String[] params = Arrays.copyOfRange(args, 2, args.length);
                currentMethod = new Method(this, methodName, returnType, params);
            } else if (currentMethod != null && line.equals("end " + currentMethod.getName())) {
                methods.add(currentMethod);
                currentMethod.parse();
                currentMethod = null;
            } else if (line.startsWith("declare")) methodParser.parse(this, line);

            else {
                if (currentMethod != null && !line.equals("") && !line.equals(" ")) currentMethod.addLine(line);
            }
        }

        if (name == null) throw new Utils.InvalidCodeException("Did not specify name for class.");
    }

    private String trimComments(String str) {
        StringBuilder fin = new StringBuilder();

        for (String word : str.split(" ")) {
            if (word.startsWith("//")) return fin.toString().trim();
            else fin.append(word).append(" ");
        }

        return fin.toString().trim();
    }

    public String getName() {
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

        throw new Utils.InvalidCodeException("Method " + name + " does not exist.");
    }

    @Override
    public String toString() {
        return "Class methods=" + Arrays.toString(methods.toArray()) + " code=" + Arrays.toString(code);
    }
}