// This class represents a block. All other blocks inherit from it (once I add inheritance... lol)
class ConditionalBlock abstract is Block

    var Value aValue
    var Value bValue
    var Comparison comparison

    constructor public (Block sB, Value a, Value b, Comparison c)
        super(sB)

        set aValue = a
        set bValue = b
        set comparison = c

    method instance boolean doComparison()
        var boolean success;

        if (comparison == Comparison.equals)
            set success = a == b
        end

        elseif (comparison == Comparison.notequals)
            set success = a != b
        end

        else
            if (a.type != PrimitiveType.double && a.type != PrimitiveType.integer)
                exception InvalidCode
            end

            if (b.type != PrimitiveType.double && b.type != PrimitiveType.integer)
                exception InvalidCode
            end

            var double aDouble
            var double bDouble

            invoke System.cast(a.value, PrimitiveType.double) aDouble
            invoke System.cast(b.value, PrimitiveType.double) bDouble

            if (comparison == Comparison.greaterthan)
                set success = aDouble > bDouble;
            end

            elseif (comparison == Comparison.lessthan)
                set success = aDouble < bDouble;
            end

            elseif (comparison == Comparison.greaterthanequalto)
                set success = aDouble >= bDouble;
            end

            elseif (comparison == Comparison.lessthanequalto)
                set success = aDouble <= bDouble;
            end

            else
                exception InvalidCode
            end
        end

        return success;

    method public string asString()
        return "ConditionalBlock"