// Represents a Class block, the top-level block.
class Class is Block

    string name

    constructor = (Block superBlock, string n)
        super(superBlock)
        name = n

    method run = () -> void
        Method main = subBlocks.get("method", "main")
        main.run()

    method asString = () -> string
        return "Class"