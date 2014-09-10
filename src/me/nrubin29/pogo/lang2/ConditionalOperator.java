package me.nrubin29.pogo.lang2;

import java.util.Arrays;

public enum ConditionalOperator {

    AND("&");

    private String token;

    private ConditionalOperator(String token) {
        this.token = token;
    }

    public static ConditionalOperator valueOfToken(String token) {
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