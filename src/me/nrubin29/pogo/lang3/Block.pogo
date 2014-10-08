// This class represents a block. All other blocks inherit from it (once I add inheritance... lol)
abstract class Block

    Block superBlock
    List(Block) subBlocks
    List(Variable) variables

    constructor = (Block sB)
        superBlock = sB
        subBlocks = new
        variables = new

    method getBlockTree = () -> List(Block)
        List(Block) tree = new
        Block b = this

        while (b != null)
            tree.add(b)
            b.getSuperBlock() b
        end

        tree.reverse()
        return tree

    abstract method run = () -> void

    abstract method asString = () -> string