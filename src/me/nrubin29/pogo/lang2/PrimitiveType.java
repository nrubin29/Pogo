package me.nrubin29.pogo.lang2;

public enum PrimitiveType implements Type {

    BOOLEAN {
        @Override
        public boolean isTokenType(String type) {
            System.out.println(type);
            return type.toLowerCase().equals("boolean");
        }
    },

    DOUBLE {
        @Override
        public boolean isTokenType(String type) {
            System.out.println(type);
            return type.toLowerCase().equals("double");
        }
    },

    INTEGER {
        @Override
        public boolean isTokenType(String type) {
            System.out.println(type);
            return type.toLowerCase().equals("integer");
        }
    },

    STRING {
        @Override
        public boolean isTokenType(String type) {
            System.out.println(type);
            return type.toLowerCase().equals("string");
        }
    },

    VOID {
        @Override
        public boolean isTokenType(String type) {
            System.out.println(type);
            return type.toLowerCase().equals(""); // I don't know...
        }
    }
}