package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.lang.Variable.VariableType;

public class Method extends Block {

    private final String name;
    private final VariableType retType;
    private final String[] params;

    private Object retValue;

    public Method(Block superBlock, String name, VariableType retType, String[] params) {
        super(superBlock);

        registerCustomLineHandler(new CustomLineHandler("return") {
            @Override
            public boolean run(String line, Block sB) throws InvalidCodeException {
                if (getReturnType() == VariableType.VOID) return true;

                getReturnType().validateValue(line.split(" ")[1], sB);
                retValue = Utils.implode(line.split(" ")[1], sB);
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
            ((Class) getBlockTree()[0]).functionManager.parse(this, "declare(" + args[0] + "," + args[1] + "," + invokeParams[i]);
//            addVariable(VariableType.match(args[0]), args[1], invokeParams[i]);
        }

        run();
        doBlocks();

        if (getReturnType() != VariableType.VOID && retValue == null)
            throw new InvalidCodeException("No return for method " + getName());

        Object localRetValue = retValue;
        retValue = null;
        return localRetValue;
    }

    @Override
    public void runAfterParse() throws InvalidCodeException {
        // We don't want to use runAfterParse() because we use invoke().
    }
}