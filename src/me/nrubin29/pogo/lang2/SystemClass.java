package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.ide.Console;

import java.io.IOException;
import java.util.List;

/**
 * This is similar to the MethodParser class in lang except that this extends Class and contains subclasses which extend Method.
 * This method is better, especially using the Parser scheme.
 */
public class SystemClass extends Class {

    private SystemClass() {
        super("System");

        add(new PrintMethod());
        add(new GetInput());
    }

    private static SystemClass instance = new SystemClass();

    public static SystemClass getInstance() {
        return instance;
    }

    private abstract class SystemMethod extends Method {

        public SystemMethod(String name, Type type, Parameter... parameters) {
            super(SystemClass.this, name, Visibility.PUBLIC, type, parameters);
        }

        @Override
        public Object invoke(List<Value> values) throws IOException, InvalidCodeException {
            super.invoke(values);
            return invoke();
        }

        protected abstract Object invoke();
    }

    private class PrintMethod extends SystemMethod {

        private PrintMethod() {
            super("print", PrimitiveType.VOID, new Parameter(PrimitiveType.STRING, "str"));
        }

        @Override
        public Object invoke() {
            Runtime.RUNTIME.print(String.valueOf(getVariable("str").get().getValue()), Console.MessageType.OUTPUT);
            return null;
        }
    }

    private class GetInput extends SystemMethod {

        private GetInput() {
            super("getInput", PrimitiveType.STRING);
        }

        @Override
        protected Object invoke() {
            return Runtime.RUNTIME.prompt();
        }
    }
}