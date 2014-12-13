package me.nrubin29.pogo.lang2.expression;

import me.nrubin29.pogo.lang2.InvalidCodeException;
import me.nrubin29.pogo.lang2.Value;
import me.nrubin29.pogo.lang2.Variable;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.block.Class;
import me.nrubin29.pogo.lang2.block.MethodInvocation;
import me.nrubin29.pogo.lang2.parser.MethodInvocationParser;
import me.nrubin29.pogo.lang2.tokenizer.Regex;
import me.nrubin29.pogo.lang2.tokenizer.Tokenizer;

import java.io.IOException;

import static me.nrubin29.pogo.lang2.tokenizer.Regex.IDENTIFIER;

/**
 * Represents an expression that must be evaluated. This could be a method call, variable, math equation, etc.
 */
public abstract class Expression {

    Tokenizer tokenizer;
    Block block;

    public Expression(Tokenizer tokenizer, Block block) {
        this.tokenizer = tokenizer;
        this.block = block;
    }

    public String getLine() {
        return tokenizer.getLine();
    }

    public Block getBlock() {
        return block;
    }

    public abstract Value evaluate() throws IOException, InvalidCodeException;

    public static Expression parse(Tokenizer tokenizer, Block block, Variable variable) throws InvalidCodeException {
        String line = tokenizer.getLine().trim();

        if (line.isEmpty()) {
            return new NullExpression();
        }

        if (line.matches(Regex.IDENTIFIER_OR_LITERAL)) {
            // It's an identifier.
            return new VariableExpression(tokenizer.nextToken(), block);
        }

        else if (line.matches(IDENTIFIER + "[.]" + IDENTIFIER + "\\((.*(,.*)*)*\\)( " + IDENTIFIER + ")?")) {
            // It's a method invocation.
            MethodInvocationParser parser = new MethodInvocationParser();
            MethodInvocation method = parser.parse(block, tokenizer);

            return new MethodExpression((((Class) block.getBlockTree()[0]).getMethod(method.getName()).get()), method.getValues());
        }

        else {
            return new InstantiationExpression(variable, tokenizer, block);
        }
    }

    @Override
    public String toString() {
        try {
            return getClass() + " evaluate()=" + evaluate();
        }

        catch (Exception e) {
            e.printStackTrace();

            return getClass().toString();
        }
    }
}