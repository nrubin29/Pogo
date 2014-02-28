package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;
import me.nrubin29.pogo.Pogo;

import java.lang.Class;

public class Variable {
	
	public enum VariableType {
		VOID(null), BOOLEAN(Boolean.class), INTEGER(Integer.class), STRING(String.class);

        private final java.lang.Class<?> clazz;

        VariableType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public static VariableType match(String str) throws InvalidCodeException {
			for (VariableType t : values()) {
				if (t.name().toLowerCase().equals(str)) return t;
			}
			
			throw new InvalidCodeException("Variable type " + str + " doesn't exist.");
		}

        public void validateValue(Object value, Block block) throws InvalidCodeException {
            try {
            	if (clazz != null) {
            		String sValue = Pogo.implode(new String[] { String.valueOf(value) }, block);
            		clazz.getDeclaredMethod("valueOf", String.class).invoke(null, sValue);
            	}
            }
            catch (Exception e) { throw new InvalidCodeException("Invalid value for variable type " + this); }
        }
	}

	private final VariableType type;
	private final String name;
    private String value;
	
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