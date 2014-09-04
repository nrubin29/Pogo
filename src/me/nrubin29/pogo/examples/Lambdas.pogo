// This code represents how custom lambdas could be used.
class Lambdas

    lambda Runnable = () -> void

    method run = (Runnable runnable) -> void
        runnable.invoke() // our Runnable lambda has no arguments and returns void, so we can invoke it like this.

    method emptyMethod = Runnable // () -> void
        // Empty...