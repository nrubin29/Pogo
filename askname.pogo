// Sample Pogo Code.

declare string name // Declaring a null string called name.

method main // Declaring the main method. This will be called when the code starts.
invoke doStuff // Invoking the doStuff method where most of the code happens.
end // Ending the main method.

method doStuff // Declaring the doStuff method.
print Enter your name. // Asking the user to enter their name.
getinput name // Setting name to whatever the user inputs.
print Hello there, _name // Printing out Hello there, and the contents of name (whatever they entered).
end // Ending the doStuff method.