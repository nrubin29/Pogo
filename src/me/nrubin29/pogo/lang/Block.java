package me.nrubin29.pogo.lang;

import me.nrubin29.pogo.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

public abstract class Block {

    private final ArrayList<CustomLineHandler> handlers;

    private final Block superBlock;
    private final ArrayList<Variable> vars;
    private final ArrayList<Block> subBlocks;
    private final ArrayList<String> lines;

    Block(Block superBlock) {
        this.handlers = new ArrayList<CustomLineHandler>();

        this.superBlock = superBlock;
        this.vars = new ArrayList<Variable>();
        this.subBlocks = new ArrayList<Block>();
        this.lines = new ArrayList<String>();
    }

    void registerCustomLineHandler(CustomLineHandler h) {
        handlers.add(h);
    }

    void addLine(String line) {
        lines.add(line);
    }

    Block getSuperBlock() {
        return superBlock;
    }

    public Block[] getBlockTree() {
        ArrayList<Block> tree = new ArrayList<Block>();

        Block b = this;

        while (b != null) {
            tree.add(b);
            b = b.getSuperBlock();
        }

        Collections.reverse(tree);

        return tree.toArray(new Block[tree.size()]);
    }

    public void addVariable(Variable.VariableType t, String name, boolean isArray, Object... values) throws Utils.InvalidCodeException {
        vars.add(new Variable(t, name, isArray, values));
    }

    public Variable getVariable(String name) throws Utils.InvalidCodeException {
        for (Block b : Arrays.copyOfRange(getBlockTree(), 0, getBlockTree().length - 1)) {
            if (b.hasVariable(name)) return b.getVariable(name);
        }

        for (Variable v : vars) {
            if (v.getName().equals(name)) return v;
        }

        throw new Utils.InvalidCodeException("Variable " + name + " is not declared.");
    }

    public boolean hasVariable(String name) {
        for (Variable v : vars) {
            if (v.getName().equals(name)) return true;
        }

        return false;
    }

    void parse() throws Utils.InvalidCodeException {
        subBlocks.clear();

        If lastIf = null;

        Block currentBlock = null;
        int numEndsIgnore = 0;

        lineLoop:
        for (String line : lines) {
            for (CustomLineHandler h : handlers) {
                if (line.startsWith(h.getStart())) {
                    if (h.run(line, this)) break lineLoop;
                    continue lineLoop;
                }
            }

            for (ConditionalBlock.ConditionalBlockType bt : ConditionalBlock.ConditionalBlockType.values()) {
                if (line.split(" ")[0].equals(bt.name().toLowerCase()) || line.split(Pattern.quote("("))[0].equals(bt.name().toLowerCase())) {
                    if (currentBlock == null) {
                        String[] splitSpace = line.substring(line.indexOf("(") + 1, line.indexOf(")")).split(" ");
                        String[] args = Arrays.copyOfRange(splitSpace, 0, splitSpace.length);

                        if (bt == ConditionalBlock.ConditionalBlockType.FOR) {
                            currentBlock = new For(this, args[0], args[1]);
                        } else if (bt == ConditionalBlock.ConditionalBlockType.FOREACH) {
                            currentBlock = new Foreach(this, args[0], args[1]);
                        } else if (bt == ConditionalBlock.ConditionalBlockType.ELSE) {
                            if (lastIf == null) throw new Utils.InvalidCodeException("Else without if.");
                            currentBlock = new Else(this);
                        } else {
                            String a = args[0], b = args[2];
                            ConditionalBlock.CompareOperation op = ConditionalBlock.CompareOperation.match(args[1]);

                            if (bt == ConditionalBlock.ConditionalBlockType.IF) {
                                currentBlock = new If(this, a, b, op);
                            } else if (bt == ConditionalBlock.ConditionalBlockType.ELSEIF) {
                                if (lastIf == null) throw new Utils.InvalidCodeException("Else if without if.");
                                currentBlock = new ElseIf(this, a, b, op);
                            } else if (bt == ConditionalBlock.ConditionalBlockType.WHILE) {
                                currentBlock = new While(this, a, b, op);
                            }
                        }
                    } else {
                        currentBlock.addLine(line);
                        numEndsIgnore++;
                    }

                    continue lineLoop;
                }
            }

            if (line.equals("end")) {
                if (numEndsIgnore > 0) {
                    numEndsIgnore--;
                    if (currentBlock != null) currentBlock.addLine("end");
                    continue;
                }

                if (currentBlock != null) {
                    currentBlock.addLine("end");
                    currentBlock.parse();

                    if (!(currentBlock instanceof Else) && !(currentBlock instanceof ElseIf)) {
                        subBlocks.add(currentBlock);
                    }

                    if (currentBlock instanceof If) {
                        lastIf = (If) currentBlock;
                    } else if (currentBlock instanceof ElseIf) {
                        lastIf.addElseIf((ElseIf) currentBlock);
                    } else if (currentBlock instanceof Else) {
                        lastIf.setElse((Else) currentBlock);
                        lastIf = null;
                    }

                    currentBlock = null;
                }

                /*
                If the end pertains to this statement...
                 */
                else break;
            } else {
                if (currentBlock != null) currentBlock.addLine(line);
                else subBlocks.add(new Line(this, line));
            }
        }
    }

    public void run() throws Utils.InvalidCodeException {
        for (Block block : subBlocks) {
            block.run();
        }
    }

    @Override
    public String toString() {
        return "Block type=" + getClass().getSimpleName();
    }
}

abstract class CustomLineHandler {
    private final String start;

    public CustomLineHandler(String start) {
        this.start = start;
    }

    public String getStart() {
        return start;
    }

    /* If true is returned, the parser stops. This is only useful for the return keyword. */
    public abstract boolean run(String line, Block sB) throws Utils.InvalidCodeException;
}