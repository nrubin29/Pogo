class Capture

    public method main = () -> void
        string name
        System.print("What is your name?")
        System.getInput() name // This will assign name to the result of System.getInput()
        System.print("Hello, {name}!")