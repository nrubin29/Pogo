package me.nrubin29.pogo.lang2.parser;

import me.nrubin29.pogo.lang2.*;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.block.ConditionalBlock;
import me.nrubin29.pogo.lang2.block.DoWhile;
import me.nrubin29.pogo.lang2.block.While;

import java.util.ArrayList;

import static me.nrubin29.pogo.lang2.Regex.COMPARISON;
import static me.nrubin29.pogo.lang2.Regex.IDENTIFIER_OR_LITERAL;

public class WhileParser extends Parser<ConditionalBlock> {

    public WhileParser() {
        super(ConditionalBlock.class);
    }

    @Override
    public boolean shouldParseLine(String line) {
        return line.matches("(while|dowhile) \\(" + IDENTIFIER_OR_LITERAL + "( )?" + COMPARISON + "( )?" + IDENTIFIER_OR_LITERAL + "( )? ((&( )?" + IDENTIFIER_OR_LITERAL + "( )?" + COMPARISON + "( )?" + IDENTIFIER_OR_LITERAL + ")*)?\\)");
    }

    @Override
    public ConditionalBlock parse(Block superBlock, PogoTokenizer tokenizer) throws InvalidCodeException {
        // while|dowhile (name == "Noah")

        Token type = tokenizer.nextToken();

        if (!tokenizer.nextToken().getToken().equals("(")) {
            throw new InvalidCodeException("While statement does not begin with opening parenthesis.");
        }

        ArrayList<Condition> conditions = new ArrayList<>();

        while (tokenizer.hasNextToken()) {
            Token a = tokenizer.nextToken();

            Comparison comparison = Comparison.valueOfToken(tokenizer.nextToken().getToken());

            Token b = tokenizer.nextToken();

            conditions.add(new Condition(a, b, comparison));

            Token token = tokenizer.nextToken();

            if (token.getToken().equals(")")) {
                break;
            }

            else {
                ConditionalOperator operator = ConditionalOperator.valueOfToken(token.getToken());

                if (operator == null) {
                    tokenizer.pushBack();
                }

                else {
                    // Do something with the operator.
                }
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