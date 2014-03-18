package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class Variable {

    private final VariableType type;
    private final String name;
    private final boolean isArray;
    private final ArrayList<Object> values;

    public Variable(VariableType type, String name, boolean isArray, Object... values) throws InvalidCodeException {
        this.type = type;
        this.name = name;
        this.isArray = isArray;

        if (!isArray && values.length > 1)
            throw new InvalidCodeException("Attempted to initialize non-array with more than one value.");

        this.values = new ArrayList<Object>(Arrays.asList(values));
    }

    public VariableType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isArray() {
        return isArray;
    }

    public Object getValue() throws InvalidCodeException {
        if (isArray) throw new InvalidCodeException("Attempted to access value of array.");
        return values.get(0);
    }

    public void setValue(Object value) throws InvalidCodeException {
        if (isArray()) throw new InvalidCodeException("Attempted to set value of array.");
        values.clear();
        values.add(getType().formatValue(value));
    }

    public Object getValue(int index) throws InvalidCodeException {
        if (!isArray) throw new InvalidCodeException("Attempted to access values of non-array.");
        if (index >= values.size()) throw new InvalidCodeException("Index does not exist.");
        return values.toArray()[index];
    }

    public Object[] getValues() throws InvalidCodeException {
        if (!isArray) throw new InvalidCodeException("Attempted to access values of non-array.");
        return values.toArray();
    }

    public void setValue(Object value, int index) throws InvalidCodeException {
        if (!isArray()) throw new InvalidCodeException("Attempted to set value at position of non-array.");
        values.add(index, getType().formatValue(value));
    }

    @Override
    public String toString() {
        return "Variable name=" + getName() + " type=" + getType() + " isArray=" + isArray + " values=" + Arrays.toString(values.toArray());
    }

    public enum VariableType {
        VOID(null), STRING(null), INTEGER(Integer.class), DECIMAL(Double.class), BOOLEAN(Boolean.class);

        private final java.lang.Class<?> clazz;

        VariableType(java.lang.Class<?> clazz) {
            this.clazz = clazz;
        }

        public static VariableType match(String str) throws InvalidCodeException {
            for (VariableType t : values()) {
                if (t.name().toLowerCase().equals(str.toLowerCase())) return t;
            }

            throw new InvalidCodeException("Variable type " + str + " doesn't exist.");
        }

        public void validateValue(Object value, Block block) throws InvalidCodeException {
            try {
                if (clazz != null) {
                    String sValue = Utils.implode(String.valueOf(value), block);
                    clazz.getDeclaredMethod("valueOf", String.class).invoke(null, sValue);
                }
            } catch (Exception e) {
                throw new InvalidCodeException("Validated invalid value " + value + " for variable type " + name().toLowerCase());
            }
        }

        public Object formatValue(Object value) throws InvalidCodeException {
            try {
                if (clazz != null) {
                    return clazz.getDeclaredMethod("valueOf", String.class).invoke(null, value);
                } else return value;
            } catch (Exception e) {
                throw new InvalidCodeException("Formatted invalid value " + value + " for variable type " + name().toLowerCase());
            }
        }
    }
}