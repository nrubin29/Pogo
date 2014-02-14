package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

import java.lang.Class;

public class Variable {
	
	public enum VariableType {
		BOOLEAN(Boolean.class), INTEGER(Integer.class), STRING(String.class);

        private java.lang.Class<?> clazz;

        VariableType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public static VariableType match(String str) throws InvalidCodeException {
			for (VariableType t : values()) {
				if (t.name().toLowerCase().equals(str)) return t;
			}
			
			throw new InvalidCodeException("Variable type " + str + " doesn't exist.");
		}

        public void validateValue(Object value) {
            try { clazz.getDeclaredMethod("valueOf", String.class).invoke(null, value); }
            catch (Exception e) { }
        }
	}

	private VariableType type;
	private String name, value;
	
	public Variable(VariableType type, String name, Object value) {
		this.type = type;
		this.name = name;
		this.value = String.valueOf(value);
	}
	
	public VariableType getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}

    public void setValue(Object value) {
        this.value = String.valueOf(value);
    }
}