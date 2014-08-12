package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.Utils;
import me.nrubin29.pogo.Utils.InvalidCodeException;
import me.nrubin29.pogo.ide.Instance;

import java.util.ArrayList;
import java.util.Arrays;

public class Variable {

    private final VariableType type;
    private final String name;
    private final boolean isArray;
    private final ArrayList<Object> values;

    public Variable(VariableType type, String name, boolean isArray, Object... values) throws Utils.InvalidCodeException {
        this.type = type;
        this.name = name;
        this.isArray = isArray;

        if (!isArray && values.length > 1) {
            throw new Utils.InvalidCodeException("Attempted to initialize non-array with more than one value.");
        }

        this.values = new ArrayList<>(Arrays.asList(Utils.replace(values.clone(), "new", Instance.CURRENT_INSTANCE.getPogoClass(type.name()))));
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

    public Object getValue() throws Utils.InvalidCodeException {
        if (isArray) throw new Utils.InvalidCodeException("Attempted to access value of array.");
        return values.get(0);
    }

    public void setValue(Object value) throws Utils.InvalidCodeException {
        if (isArray()) throw new Utils.InvalidCodeException("Attempted to set value of array.");
        values.clear();
        values.add(getType().formatValue(value));
    }

    public Object getValue(int index) throws Utils.InvalidCodeException {
        if (!isArray) throw new Utils.InvalidCodeException("Attempted to access values of non-array.");
        if (index >= values.size()) throw new Utils.InvalidCodeException("Index does not exist.");
        return values.toArray()[index];
    }

    public Object[] getValues() throws Utils.InvalidCodeException {
        if (!isArray) throw new Utils.InvalidCodeException("Attempted to access values of non-array.");
        return values.toArray();
    }

    public void setValue(Object value, int index) throws Utils.InvalidCodeException {
        if (!isArray()) throw new Utils.InvalidCodeException("Attempted to set value at position of non-array.");
        values.add(index, getType().formatValue(value));
    }

    @Override
    public String toString() {
        return "Variable name=" + getName() + " type=" + getType() + " isArray=" + isArray + " values=" + Arrays.toString(values.toArray());
    }

    public enum SystemVariableType implements VariableType {
        VOID(null), STRING(null), INTEGER(Integer.class), DECIMAL(Double.class), BOOLEAN(Boolean.class);

        private final java.lang.Class<?> clazz;

        SystemVariableType(java.lang.Class<?> clazz) {
            this.clazz = clazz;
        }

        public static VariableType match(String str) throws Utils.InvalidCodeException {
            for (SystemVariableType t : values()) {
                if (t.name().toLowerCase().equals(str.toLowerCase())) return t;
            }

            throw new Utils.InvalidCodeException("System variable type " + str + " does not exist.");
        }

        @Override
        public void validateValue(Object value, Block block) throws Utils.InvalidCodeException {
            try {
                if (clazz != null) {
                    String sValue = Utils.implode(String.valueOf(value), block);
                    clazz.getDeclaredMethod("valueOf", String.class).invoke(null, sValue);
                }
            } catch (Exception e) {
                throw new Utils.InvalidCodeException("Validated invalid value " + value + " for system variable type " + name().toLowerCase());
            }
        }

        @Override
        public Object formatValue(Object value) throws Utils.InvalidCodeException {
            try {
                if (clazz != null) {
                    return clazz.getDeclaredMethod("valueOf", String.class).invoke(null, value);
                } else return value;
            } catch (Exception e) {
                throw new Utils.InvalidCodeException("Formatted invalid value " + value + " for system variable type " + name().toLowerCase());
            }
        }
    }

    public interface VariableType {
        public void validateValue(Object value, Block block) throws Utils.InvalidCodeException;

        public Object formatValue(Object value) throws Utils.InvalidCodeException;

        public String name();

        public static class VariableTypeMatcher {
            public static VariableType match(String str) throws InvalidCodeException {
                try {
                    return SystemVariableType.match(str);
                } catch (InvalidCodeException e) {
                    if (Instance.CURRENT_INSTANCE.getPogoClass(str) != null) {
                        return Instance.CURRENT_INSTANCE.getPogoClass(str);
                    } else {
                        throw new InvalidCodeException("No variable type for " + str + ".");
                    }
                }
            }
        }
    }
}