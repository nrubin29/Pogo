package me.nrubin29.pogo.lang2.expression;

import me.nrubin29.pogo.lang2.*;
import me.nrubin29.pogo.lang2.block.Block;
import me.nrubin29.pogo.lang2.block.Class;
import me.nrubin29.pogo.lang2.block.MethodInvocation;
import me.nrubin29.pogo.lang2.parser.MethodInvocationParser;

import java.io.IOException;

import static me.nrubin29.pogo.lang2.Regex.IDENTIFIER;

/**
 * Represents an expression that must be evaluated. This could be a method call, variable, math equation, etc.
 */
public abstract class Expression {

    public abstract Value evaluate() throws IOException, InvalidCodeException;

    public static Expression parse(PogoTokenizer tokenizer, Block block, Variable variable) throws InvalidCodeException {
        String line = tokenizer.getLine().trim();

        System.out.println("Expression line: " + line);

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