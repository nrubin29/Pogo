package me.nrubin29.pogo.lang2.tokenizer;

import java.util.regex.Pattern;

public class StringTokenizer extends Tokenizer {

    public StringTokenizer(String str) {
        super(str);

        registerTokenData(new TokenData(Pattern.compile("^(\\{[^{]*\\})"), Token.TokenType.IDENTIFIER));
        registerTokenData(new TokenData(Pattern.compile("^([^\\{]*)"), Token.TokenType.STRING_PART));
    }
}