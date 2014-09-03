class Value

    Type type
    object value

    constructor public (object v)
        value = v

    constructor public (Type t, object v)
        type = t
        value = v

    method public object getValue()
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

    method public void setValue(object v)
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