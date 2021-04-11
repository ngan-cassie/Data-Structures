## Description of SIMPLE 0

### How to run the program
- Run on the terminal: “java Simple0 [test  file name]” (e.g. “java Simple0 test1.txt”)

## Finding the GCD
This sample test case makes use of the jumpn instruction. We used jumpz to do equality tests by subtracting two numbers, and then checking if the zero flag was set; with the jumpn instruction we can do greater than or less than comparisons. The first two instructions push the two numbers to find the greatest common divisor of.  If you run it as is, you get the equivalent of calling gcd(25, 35) which should return 5.  Run a few more trials with different numbers by changing the first two numbers pushed.

push 35\
push 25
store 0
store 1
load 1
load 0
subtract
jumpn 8
pop
load 1
store 2
load 0
store 1
load 2
store 0
load 0
load 1
subtract
store 0
load 1
load 0
subtract
jumpz 3
push 0
jumpz -20
load 0
out
