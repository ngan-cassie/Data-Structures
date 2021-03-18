/*
Name: Ngan Nguyen
Email: nguyenh0@sewanee.edu
Course: CSCI257
Instructor: Prof. Carl
File: Project 1 - Simple 0 simulator
 */

import java.util.*;
import java.io.*;

public class Simple0 {
    private ArrayList<Byte> main_memory;
    private Stack<Byte> processor;
    private boolean zero; // Last instruction executed leaves a 0 on top of the stack
    private boolean overflow; // last instruction resulted in a number that can't be represented properly in 8 bits
    private boolean negate; // last instruction resulted in a negative number
    private boolean crash; // last instruction overflowed or underflowed the stack

    public Simple0() {
        main_memory =  getMainMemory();
        processor = new Stack<>();
        zero = false;
        overflow = false;
        negate = false;
        crash = false;
    }

    // Initialize the main memory with space for 1024 8-bit numbers
    public ArrayList<Byte> getMainMemory() {
        main_memory =  new ArrayList<>(10);
        for (int i = 0; i < 10; i++)
            main_memory.add((byte)0);
        return main_memory;
    }

    // Read instructions from a file into an ArrayList
    public ArrayList<String> readFile(String file_name) {
        try {
            File f = new File(file_name);
            Scanner reader = new Scanner(f);
            ArrayList<String> command = new ArrayList<>();
            while (reader.hasNext()) {
                command.add(reader.nextLine());
            }
            return command;
        }
        catch (IOException ex) {
            // opening the file didn't work
            throw new RuntimeException(ex.toString());
        }
    }
    // Read through the ArrayList and execute the next instruction
    public void execute(ArrayList<String> list) {
        try {
            int i = 0;
            while (i < list.size()) {
                String instr = list.get(i);
                if (instr.equals("pop")) {
                    // pop a number from the stack
                    this.processor.pop();
                } else if (instr.equals("add")) {
                    add();
                } else if (instr.equals("subtract")) {
                    subtract();
                } else if (instr.equals("multiply")) {
                    multiply();
                } else if (instr.equals("divide")) {
                    divide();
                } else if (instr.equals("in")) {
                    // reads a number from the keyboard and pushes onto the stack
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Type in a number from -128 to 127: ");
                    Byte inp = sc.nextByte();
                    this.processor.push(inp);
                } else if (instr.equals("out")) {
                    // pops the top of stack and outputs the value to the screen
                    System.out.println((byte) this.processor.pop());
                } else if (instr.equals("dump")) {
                    // displays the current contents of the stack and processor flags on the screen
                    System.out.println(Arrays.toString(this.processor.toArray()));
                    System.out.println("Zero: " + this.zero);
                    System.out.println("Overflow: " + this.overflow);
                    System.out.println("Negate: " + this.negate);
                    System.out.println("Crash: " + this.crash);
                } else if (instr.startsWith("push")) {
                    // pushes a number onto the stack
                    int val = splitString(instr);
                    this.checkFlags(val);
                    this.processor.push((byte) val);
                } else if (instr.startsWith("load")) {
                    // load the value from location addr in memory onto the stack
                    int val = this.main_memory.get(splitString(instr));
                    this.processor.push((byte) val);
                    this.checkFlags(val);
                } else if (instr.startsWith("store")) {
                    // pop a number off the stack and place it into location addr in memory
                    this.main_memory.set(splitString(instr), this.processor.pop());
                } else if (instr.startsWith("jumpz")) {
                    int val = splitString(instr);
                    // jump forward/backward [value] instructions if Zero == true
                    if (this.zero) {
                        i += val;
                        continue;
                    }
                } else if (instr.startsWith("jumpn")) {
                    int val = splitString(instr);
                    // jump forward/backward [value] instructions if Negate == true
                    if (this.negate) {
                        i += val;
                        continue;
                    }
                }
                i += 1;
            }
        }
        catch (EmptyStackException ex) {
            this.crash = true;
            System.out.println("Your program is crashed!");
            throw new RuntimeException(ex.toString());
        }
    }

    /* SMALLER METHODS */

    // pops the top two numbers from the stack, adds them, and pushes the sum
    public void add() {
        int n1 = this.processor.pop();
        int n2 = this.processor.pop();
        this.checkFlags(n1+n2);
        this.processor.push((byte) (n1 + n2));
    }
    // pops the top two numbers from the stack, subtracts the first one popped from the second, and pushes the result
    public void subtract() {
        int n1 = this.processor.pop();
        int n2 = this.processor.pop();
        this.checkFlags(n2-n1);
        this.processor.push((byte) (n2 - n1));
    }
    // pops the top two numbers from the stack, multiplies them, and pushes the result
    public void multiply() {
        int n1 = this.processor.pop();
        int n2 = this.processor.pop();
        this.checkFlags(n1*n2);
        this.processor.push((byte) (n1 * n2));
    }
    // pops the top two numbers on the stack, integer divides the second by the first, and pushes the result
    public void divide() {
            int n1 = this.processor.pop();
            int n2 = this.processor.pop();
            this.processor.push((byte) (n2 / n1));
    }
    // split the instruction that comes with a value and return that value
    public int splitString(String e) {
        String[] val = e.split(" ");
        return Integer.parseInt(val[1]);
    }
    // check the processor flags
    public void checkFlags(int check){
        this.zero = check == 0;
        this.negate = (byte) check < 0;
        if (check > 127 || check < -128)
            this.overflow = true;
    }

    /* MAIN METHOD */
    public static void main(String[] args) {
        Simple0 simple0 = new Simple0();
        ArrayList<String> cmd = simple0.readFile(args[0]);
        simple0.execute(cmd);
            }
    }

