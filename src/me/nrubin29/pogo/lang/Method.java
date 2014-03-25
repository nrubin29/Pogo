package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.lang.Variable.SystemVariableType;
import me.nrubin29.pogo.lang.Variable.VariableType;

import java.util.Arrays;

public class Method extends Block {

    private final Visibility vis;
    private final String name;
    private final VariableType retType;
    private final String[] params;

    private Object retValue;

    public Method(Block superBlock, Visibility vis, String name, VariableType retType, String[] params) {
        super(superBlock);

        registerCustomLineHandler(new CustomLineHandler("return") {
            @Override
            public boolean run(String line, Block sB) throws Utils.InvalidCodeException {
                if (getReturnType() == SystemVariableType.VOID) return true;

                getReturnType().validateValue(line.split(" ")[1], sB);
                retValue = Utils.implode(line.split(" ")[1], sB);
                return true;
            }
        });

        this.vis = vis;
        this.name = name;
        this.retType = retType;
        this.params = params;
    }

    public Visibility getVisibility() {
        return vis;
    }

    public String getName() {
        return name;
    }

    public VariableType getReturnType() {
        return retType;
    }

    public synchronized Object invoke(Object[] invokeParams) throws Utils.InvalidCodeException {
        if (invokeParams.length != params.length)
            throw new Utils.InvalidCodeException("Wrong number of parameters supplied.");

        /*
        integer:i
        string:str
         */
        for (int i = 0; i < params.length; i++) {
            String[] args = params[i].split(":");
            ((Class) getBlockTree()[0]).methodParser.parse(this, "declare(" + args[0] + "," + args[1] + "," + invokeParams[i] + ")");
        }

        super.run();

        if (getReturnType() != SystemVariableType.VOID && retValue == null) {
            throw new Utils.InvalidCodeException("No return for method " + getName());
        }

        Object localRetValue = retValue;
        retValue = null;
        return localRetValue;
    }

    @Override
    public String toString() {
        return "Method name=" + getName() + " returnType=" + getReturnType() + " params=" + Arrays.toString(params);
    }

    public enum Visibility {
        PUBLIC, INSTANCE, PRIVATE;

        public static Visibility match(String str) throws Utils.InvalidCodeException {
            for (Visibility v : values()) {
                if (v.name().toLowerCase().equals(str.toLowerCase())) return v;
            }

            throw new Utils.InvalidCodeException("Visibility " + str + " does not exist.");
        }
    }
}