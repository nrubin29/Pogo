package me.nrubin29.pogo.lang2.tokenizer;

import me.nrubin29.pogo.lang2.InvalidCodeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class PreProcessedTokenizer extends Tokenizer {

    private Stack<Token> tokens;
    private Token lastToken;
    private boolean pushBack;

    public PreProcessedTokenizer(ArrayList<Token> tokens) {
        this(tokens.toArray(new Token[tokens.size()]));
    }

    public PreProcessedTokenizer(Token... tokens) {
        super(String.join(" ", Arrays.stream(tokens).map(Token::getToken).toArray(CharSequence[]::new)));

        this.tokens = new Stack<>();
        Collections.addAll(this.tokens, tokens);
    }

    @Override
    public Token nextToken() throws InvalidCodeException {
        if (pushBack) {
            pushBack = false;
            return lastToken;
        }

        if (tokens.isEmpty()) {
            return lastToken = new Token(Token.TokenType.EMPTY);
        }

        return tokens.pop();
    }

    @Override
    public boolean hasNextToken() {
        return !tokens.isEmpty();
    }

    @Override
    public void pushBack() {
        if (lastToken != null) {
            this.pushBack = true;
        }
    }

    @Override
    public int getNumberOfTokens(Token.TokenType... types) throws InvalidCodeException {
        Stack<Token> localTokens = (Stack<Token>) tokens.clone();
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

        this.tokens = localTokens;
        return count;
    }

    @Override
    public PreProcessedTokenizer clone() {
        return new PreProcessedTokenizer(tokens.toArray(new Token[tokens.size()]));
    }
}