package me.nrubin29.pogo.lang2;

import java.util.Arrays;

public enum Comparison {

    EQUALS("=="), NOTEQUALS("!="), GREATERTHAN(">"), LESSTHAN("<"), GREATERTHANEQUALTO(">="), LESSTHANEQUALTO("<=");

    private String token;

    private Comparison(String token) {
        this.token = token;
    }

    public static Comparison valueOfToken(String token) {
        return Arrays.stream(values())
                .filter(comparison -> comparison.toString().equals(token))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return token;
    }
}