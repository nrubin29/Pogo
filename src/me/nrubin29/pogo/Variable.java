package me.nrubin29.pogo;

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
	private String name;
	private Object value;
	
	public Variable(VariableType type, String name, Object value) {
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
	
	public Object getValue() {
		return value;
	}
}