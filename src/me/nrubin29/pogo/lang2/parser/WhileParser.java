package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.Comparison;
import me.nrubin29.pogo.lang2.Condition;
import me.nrubin29.pogo.lang2.ConditionalOperator;
import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.block.ConditionalBlock;
import me.nrubin29.pogo.lang2.block.DoWhile;
import me.nrubin29.pogo.lang2.block.While;
import me.nrubin29.pogo.lang2.expression.Expression;
import me.nrubin29.pogo.lang2.tokenizer.PreProcessedTokenizer;
import me.nrubin29.pogo.lang2.tokenizer.Token;
import me.nrubin29.pogo.lang2.tokenizer.Tokenizer;

import java.util.ArrayList;

import static me.nrubin29.pogo.lang2.tokenizer.Regex.COMPARISON;
import static me.nrubin29.pogo.lang2.tokenizer.Regex.IDENTIFIER_OR_LITERAL;

public class WhileParser extends Parser<ConditionalBlock> {

    public WhileParser() {
        super(ConditionalBlock.class);
    }

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches("(while|dowhile) \\(" + IDENTIFIER_OR_LITERAL + "( )?" + COMPARISON + "( )?" + IDENTIFIER_OR_LITERAL + "( )? ((&( )?" + IDENTIFIER_OR_LITERAL + "( )?" + COMPARISON + "( )?" + IDENTIFIER_OR_LITERAL + ")*)?\\)");
    }

    @Override
    public ConditionalBlock parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
        // while|dowhile (name == "Noah")

        Token type = tokenizer.nextToken();

        if (!tokenizer.nextToken().getToken().equals("(")) {
            throw new InvalidCodeException("While statement does not begin with opening parenthesis.");
        }

        ArrayList<Condition> conditions = new ArrayList<>();

        ArrayList<Token> a = new ArrayList<>(), b = new ArrayList<>();
        Comparison comparison = null;
        boolean isA = true;

        while (tokenizer.hasNextToken()) {
            Token token = tokenizer.nextToken();

            ConditionalOperator operator = ConditionalOperator.valueOfToken(token.getToken());

            if (operator != null || token.getToken().equals(")")) {
                /*
                This might cause an issue with superBlock.
                */
                conditions.add(new Condition(Expression.parse(new PreProcessedTokenizer(a), superBlock, null), Expression.parse(new PreProcessedTokenizer(b), superBlock, null), comparison));

                a.clear();
                b.clear();
                comparison = null;
                isA = true;

                if (token.getToken().equals(")")) {
                    break;
                }

                else {
                    continue;
                }
            }

            if (Comparison.valueOfToken(token.getToken()) == null) {
                if (isA) {
                    a.add(token);
                }

                else {
                    b.add(token);
                }
            }

            else {
                comparison = Comparison.valueOfToken(token.getToken());
                isA = false;
            }
        }

        if (type.getToken().equals("while")) {
            return new While(superBlock, conditions.toArray(new Condition[conditions.size()]));
        }

        else {
            return new DoWhile(superBlock, conditions.toArray(new Condition[conditions.size()]));
        }
    }
}