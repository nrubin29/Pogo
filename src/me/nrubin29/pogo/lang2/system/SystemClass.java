package me.nrubin29.pogo.lang2.system;

import me.nrubin29.pogo.ide.Console;
import me.nrubin29.pogo.lang2.*;
import me.nrubin29.pogo.lang2.Runtime;
import me.nrubin29.pogo.lang2.block.Class;

/**
 * This is similar to the MethodParser class in lang except that this extends Class and contains subclasses which extend Method.
 * This method is better, especially using the Parser scheme.
 */
public class SystemClass extends Class {

    private SystemClass() {
        super("System");

        add(new Print());
        add(new GetInput());
    }

    private static SystemClass instance = new SystemClass();

    public static SystemClass getInstance() {
        return instance;
    }

    private class Print extends SystemMethod {

        private Print() {
            super(SystemClass.this, "print", PrimitiveType.VOID, new Parameter(PrimitiveType.OBJECT, "msg"));
        }

        @Override
        public Value invoke() throws InvalidCodeException {
            me.nrubin29.pogo.lang2.Runtime.RUNTIME.print(String.valueOf(getVariable("msg").get().getValue()), Console.MessageType.OUTPUT);
            removeVariable(getVariable("msg").get());
            return null;
        }
    }

    private class GetInput extends SystemMethod {

        private GetInput() {
            super(SystemClass.this, "getInput", PrimitiveType.STRING);
        }

        @Override
        protected Value invoke() {
            return new Value(PrimitiveType.STRING, Runtime.RUNTIME.prompt());
        }
    }
}