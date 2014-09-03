// This code represents how custom lambdas could be used.
class Lambdas

    lambda Runnable = () -> void

    public method run = (Runnable runnable) -> void
        runnable.invoke() // our Runnable lambda has no arguments and returns void, so we can invoke it like this.

    public method emptyMethod = Runnable // () -> void
        // Empty...