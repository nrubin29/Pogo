// This class represents a block. All other blocks inherit from it (once I add inheritance... lol)
abstract class ConditionalBlock is Block

    Value aValue
    Value bValue
    Comparison comparison

    constructor = (Block sB, Value a, Value b, Comparison c)
        super(sB)

        aValue = a
        bValue = b
        comparison = c

    @instance method doComparison = () -> boolean
        boolean success

        if (comparison == Comparison.equals)
            success = a == b
        end

        elseif (comparison == Comparison.notequals)
            success = a != b
        end

        else
            if (a.type != PrimitiveType.double && a.type != PrimitiveType.integer)
                exception InvalidCode
            end

            if (b.type != PrimitiveType.double && b.type != PrimitiveType.integer)
                exception InvalidCode
            end

            double aDouble
            double bDouble

            System.cast(a.value, PrimitiveType.double) aDouble
            System.cast(b.value, PrimitiveType.double) bDouble

            if (comparison == Comparison.greaterthan)
                success = aDouble > bDouble
            end

            elseif (comparison == Comparison.lessthan)
                success = aDouble < bDouble
            end

            elseif (comparison == Comparison.greaterthanequalto)
                success = aDouble >= bDouble
            end

            elseif (comparison == Comparison.lessthanequalto)
                success = aDouble <= bDouble
            end

            else
                exception InvalidCode
            end
        end

        return success

    method asString = () -> string
        return "ConditionalBlock"