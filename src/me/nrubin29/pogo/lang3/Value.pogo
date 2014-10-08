class Value

    Type type
    object value

    constructor = (object v)
        value = v

    constructor = (Type t, object v)
        type = t
        value = v

    method getValue = () -> object
        if (type == null || value == null)
            return value

        if (type == PrimitiveType.boolean)
            return boolean.valueOf(value)

        elseif (type == PrimitiveType.double)
            return double.valueOf(value)

        elseif (type == PrimitiveType.integer)
            return integer.valueOf(value)

        elseif (type == PrimitiveType.string)
            return string.valueOf(value)

        else
            return value

    method setValue = (object v) -> void
        if (type == null || value == null)
            value = v
            return

        if (type == PrimitiveType.boolean)
            value = boolean.valueOf(v)

        elseif (type == PrimitiveType.double)
            value = double.valueOf(v)

        elseif (type == PrimitiveType.integer)
            value = integer.valueOf(v)

        elseif (type == PrimitiveType.string)
            value = string.valueOf(v)

        else
            value = v