// Represents a Class block, the top-level block.
class Class is Block

    var string name

    constructor public (Block superBlock, string n)
        super(superBlock)
        set name = n

    method public void run()
        var Method main
        invoke subBlocks.get("method", "main") main
        invoke main.run()

    method public string asString()
        return "Class"