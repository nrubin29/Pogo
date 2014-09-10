class IfStatements

    method main = () -> void
        string name = "Noah"
        if (name == "Noah")
            System.print("You rock, Noah!")
        end
        else
            System.print("I don't know you.")
        end

        integer age = 15

        if (name == "Noah" & age == 15)    // This compound if statement checks both conditions based on the operators.
            System.print("Almost time...") // The ampersand denotes that both conditions must be true.
        end                                // In this case, if they are both true, the body runs.

        elseif (name == "Noah" & age == 16)
            System.print("Time to drive!")
        end