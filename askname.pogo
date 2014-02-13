// Sample Pogo Code.

declare string name
declare integer favorite = 29
declare boolean awesome = true

method main
invoke doStuff
end main

method doStuff
print Enter your name.
getinput name
print Hello there, _name
invoke checkName
end doStuff

method checkName
if equals _name Noah
print Nice to see you.
end
end checkName