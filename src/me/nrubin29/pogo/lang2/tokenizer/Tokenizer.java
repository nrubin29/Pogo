package me.nrubin29.pogo.lang2.tokenizer;

import me.nrubin29.pogo.lang2.InvalidCodeException;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class Tokenizer implements Cloneable {

    private ArrayList<TokenData> tokenDatas;
    String str;
    private Token lastToken;
    private boolean pushBack;

    public Tokenizer(String str) {
        this.tokenDatas = new ArrayList<>();
        this.str = str;
    }

    public Token nextToken() throws InvalidCodeException {
        str = str.trim();

        if (pushBack) {
            pushBack = false;
            return lastToken;
        }

        if (str.equals("")) {
            return lastToken = new Token(Token.TokenType.EMPTY);
        }

        for (TokenData data : tokenDatas) {
            Matcher matcher = data.getPattern().matcher(str);

            if (matcher.find()) {
                String token = matcher.group().trim();
                str = matcher.replaceFirst("");

                if (data.getTokenType() == Token.TokenType.STRING_LITERAL) {
                    return lastToken = new Token(Token.TokenType.STRING_LITERAL, token.substring(1, token.length() - 1));
                }

                else {
                    return lastToken = new Token(data.getTokenType(), token);
                }
            }
        }

        throw new InvalidCodeException("Unknown token: " + str.substring(0, str.indexOf(" ")));
    }

    public boolean hasNextToken() {
        return !str.equals("");
    }

    public void pushBack() {
        if (lastToken != null) {
            this.pushBack = true;
        }
    }

    public int getNumberOfTokens(Token.TokenType... types) throws InvalidCodeException {
        String localStr = str;
        int count = 0;

        while (hasNextToken()) {
            Token token = nextToken();

            for (Token.TokenType type : types) {
                if (token.getType().equals(type)) {
                    count++;
                    break;
                }
            }
        }

        this.str = localStr;
        return count;
    }

    public String getLine() {
        return str;
    }

    public void registerTokenData(TokenData data) {
        tokenDatas.add(data);
    }

    @Override
    public Tokenizer clone() {
        return new Tokenizer(str);
    }
}