// Represents a Class block, the top-level block.
class Class is Block

    string name

    constructor public (Block superBlock, string n)
        super(superBlock)
        name = n

    method public void run()
        Method main
        subBlocks.get("method", "main") main
        main.run()

    method public string asString()
        return "Class"