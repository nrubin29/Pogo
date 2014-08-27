// Represents a Class block, the top-level block.
class Comparison

    var string token

    var Comparison equals = new("==")
    var Comparison notEquals = new("!=")
    var Comparison greaterThan = new(">")
    var Comparison lessThan = new("<")
    var Comparison greaterThanEqualTo = new(">=")
    var Comparison lessThanEqualTo = new("<=")

    constructor private (string t)
        set token = t

    method public Comparison valueOf(string t)
        if (t == "==")
            return equals
        end

        elseif (t == "!=")
            return notEquals
        end

        elseif (t == ">")
            return greaterThan
        end

        elseif (t == "<")
            return lessThan
        end

        elseif (t == ">=")
            return greaterThanEqualTo
        end

        elseif (t == "<=")
            return lessThanEqualTo
        end

        else
            exception InvalidCode

    method public string asString()
        return token