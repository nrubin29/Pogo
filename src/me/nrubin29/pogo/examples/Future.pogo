// This code represents my dream of how Pogo would be. This will probably never happen.
class HelloWorld

    Method main = () -> void
        @final string name = "Noah"
        name = "haoN" // This causes a FinalVariableChange exception.

// Here's an example of a property.
property final

    Method applyToVariable = (Variable v) -> void
        v.meta.setOnValueChange(onValueChange)

    @private Method onValueChange = (Variable variable, Value oldValue, Value newValue) -> void
        exception FinalVariableChange

// Let's break down a few important differences. First, methods are now objects. Instead of a method trigger word,
// methods are declared in the same way as strings are. In the main method, the () means that it takes no arguments.
// The -> is denoting what happens with the arguments. The void is the return type of the method.
// Everything below that represents the body of the method. You could manually call the main method by writing the following:

HelloWorld.main.invoke()

// If there were arguments, you would specify them in the invoke call. If the method returned a value, you could specify
// a capture.

// Second, properties: object-oriented reserved words. In the example, we made a final property which, when applied to a variable,
// makes its value unable to be changed. The applyToVariable method is automatically called when the property is applied to a
// variable. It uses the meta (more on that later) of the variable and registers a system listener on it. When a value is changed,
// a FinalVariableChange exception occurs.

// Third, meta: similar to Reflection in Java but more powerful. Each type has its own type of meta; for example, VariableMeta,
// MethodMeta, etc. You can register system listeners on different types of meta.