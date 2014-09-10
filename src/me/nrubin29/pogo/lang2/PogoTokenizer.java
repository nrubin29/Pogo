package me.nrubin29.pogo.lang2;

import java.util.regex.Pattern;

import static me.nrubin29.pogo.lang2.Regex.*;

public class PogoTokenizer extends Tokenizer {

    public PogoTokenizer(String str) {
        super(str);

        registerTokenData(new TokenData(Pattern.compile("^(" + COMPARISON + ")"), Token.TokenType.TOKEN));
        registerTokenData(new TokenData(Pattern.compile("^(" + CONDITIONAL_OPERATOR + ")"), Token.TokenType.TOKEN));

        for (String t : new String[] { "=", "\\(", "\\)", "\\.", "\\,", "->" }) {
            registerTokenData(new TokenData(Pattern.compile("^(" + t + ")"), Token.TokenType.TOKEN));
        }

        registerTokenData(new TokenData(Pattern.compile("^(" + IDENTIFIER + ")"), Token.TokenType.IDENTIFIER));
        registerTokenData(new TokenData(Pattern.compile("^(//.*)"), Token.TokenType.EMPTY));
        registerTokenData(new TokenData(Pattern.compile("^(" + PROPERTY + ")"), Token.TokenType.PROPERTY));
        registerTokenData(new TokenData(Pattern.compile("^(" + STRING_LITERAL + ")"), Token.TokenType.STRING_LITERAL));
        registerTokenData(new TokenData(Pattern.compile("^(" + BOOLEAN_LITERAL + ")"), Token.TokenType.BOOLEAN_LITERAL));
        registerTokenData(new TokenData(Pattern.compile("^(" + INTEGER_LITERAL + ")"), Token.TokenType.INTEGER_LITERAL));
        registerTokenData(new TokenData(Pattern.compile("^(" + DOUBLE_LITERAL + ")"), Token.TokenType.DOUBLE_LITERAL));
    }

    @Override
    public PogoTokenizer clone() {
        return new PogoTokenizer(str);
    }
}