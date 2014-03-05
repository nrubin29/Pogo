package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Pogo;
import me.nrubin29.pogo.lang.Variable.VariableType;

public class Method extends Block {

    private final String name;
    private final VariableType retType;
    private final String[] params;

    private Object retValue;

    public Method(Block superBlock, String name, VariableType retType, String[] params) {
        super(superBlock);

        registerCustomLineHandler(new CustomLineHandler("return") {
            public boolean run(String line, Block sB) throws InvalidCodeException {
                if (getReturnType() == VariableType.VOID) return true;

                getReturnType().validateValue(line.split(" ")[1], sB);
                retValue = Pogo.implode(new String[]{line.split(" ")[1]}, sB);
                return true;
            }
        });

        this.name = name;
        this.retType = retType;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public VariableType getReturnType() {
        return retType;
    }

    public synchronized Object invoke(Object[] invokeParams) throws InvalidCodeException {
        /*
        integer:i
        string:str
         */
        for (int i = 0; i < params.length; i++) {
            String[] args = params[i].split(":");
            addVariable(VariableType.match(args[0]), args[1], invokeParams[i]);
        }

        run();
        doBlocks();

        if (getReturnType() != VariableType.VOID && retValue == null)
            throw new InvalidCodeException("No return for method " + getName());

        Object localRetValue = retValue;
        retValue = null;
        return localRetValue;
    }

    public void runAfterParse() throws InvalidCodeException {
        // We don't want to use runAfterParse() because we use invoke().
    }
}