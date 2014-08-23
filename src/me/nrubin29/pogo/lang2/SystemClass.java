package me.nrubin29.pogo.lang2;

import me.nrubin29.pogo.Pogo;
import me.nrubin29.pogo.ide.Console;

/**
 * This is similar to the MethodParser class in lang except that this extends Class and contains subclasses which extend Method.
 * This method is better, especially using the Parser scheme.
 */
public class SystemClass extends Class {

    private SystemClass() {
        super("System");

        add(new PrintMethod());
    }

    private static SystemClass instance = new SystemClass();

    public static SystemClass getInstance() {
        return instance;
    }

    private class PrintMethod extends Method {

        private PrintMethod() {
            super(SystemClass.this, "print", Visibility.PUBLIC, PrimitiveType.VOID, new Parameter[] { new Parameter(PrimitiveType.STRING, "str") });
        }

        @Override
        public void run() {
            Pogo.getIDE().getConsole().write("Print method called.", Console.MessageType.OUTPUT);
        }
    }
}