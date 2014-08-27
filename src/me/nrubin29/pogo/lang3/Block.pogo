// This class represents a block. All other blocks inherit from it (once I add inheritance... lol)
class Block abstract

    var Block superBlock
    var List(Block) subBlocks
    var List(Variable) variables

    constructor public (Block sB)
        set superBlock = sB
        set subBlocks = new
        set variables = new

    method public List(Block) getBlockTree()
        var List(Block) tree = new
        var Block b = this;

        while (b != null)
            invoke tree.add(b);
            invoke b.getSuperBlock() b
        end

        invoke tree.reverse()
        return tree

    abstractmethod public void run()

    abstractmethod public string asString()