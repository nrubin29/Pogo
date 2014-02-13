package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.InvalidCodeException;

public class Variable {
	
	public enum VariableType {
		STRING;
		
		public static VariableType match(String str) throws InvalidCodeException {
			for (VariableType t : values()) {
				if (t.name().toLowerCase().equals(str)) return t;
			}
			
			throw new InvalidCodeException("Variable type doesn't exist.");
		}
	}

	private VariableType type;
	private String name, value;
	
	public Variable(VariableType type, String name, String value) {
		this.type = type;
		this.name = name;
		this.value = value;
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

    public void setValue(String value) {
        this.value = value;
    }
}