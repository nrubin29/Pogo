// Represents a Class block, the top-level block.
class Comparison

    string token

    Comparison equals = new("==")
    Comparison notEquals = new("!=")
    Comparison greaterThan = new(">")
    Comparison lessThan = new("<")
    Comparison greaterThanEqualTo = new(">=")
    Comparison lessThanEqualTo = new("<=")

    @private constructor = (string t)
        token = t

    method valueOf = (string t) -> Comparison
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

    method asString = () -> string
        return token