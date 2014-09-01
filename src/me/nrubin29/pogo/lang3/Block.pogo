// This class represents a block. All other blocks inherit from it (once I add inheritance... lol)
class Block abstract

    Block superBlock
    List(Block) subBlocks
    List(Variable) variables

    constructor public (Block sB)
        superBlock = sB
        subBlocks = new
        variables = new

    method public List(Block) getBlockTree()
        List(Block) tree = new
        Block b = this

        while (b != null)
            tree.add(b)
            b.getSuperBlock() b
        end

        tree.reverse()
        return tree

    abstractmethod public void run()

    abstractmethod public string asString()