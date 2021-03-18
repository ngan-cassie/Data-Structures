/*
 * Name: Ngan Nguyen
 * Email: nguyenh0@sewanee.edu
 * Class: CSCI257
 * Instructor: Prof. Carl
 * File: Stack.java
 * ----------------
 * This file simulates the Stack class from the java.util package.
 */
/**
 * @author Ngan Nguyen
 * @version
 */
import java.util.*;
import java.util.NoSuchElementException;

public class Stack<T>  { // the <T> means this is a generic class

    /**
     * Creates a new empty stack.
     */
    public Stack() {
        capacity = INITIAL_CAPACITY;
        array = new ArrayList<T>(capacity);
        count = 0;
    }

    /**
     * @postcondition returns the number of elements currently on the stack
     */
    public int size() {
        return count;
    }

    /**
     * @postcondition returns true if the stack is empty
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * @postcondition removes all the elements from a stack
     */
    public void clear() {
        count = 0;
    }

    /*
     * Note: these methods manipulate the contents of the underlying array.  The push
     * method checks the capacity; pop checks for an empty stack.
     */
    /**
     * @param value
     * @postcondition pushes the value on the stack so that it becomes the topmost element
     */
    public void push(T value) {
        if (count == capacity) expandCapacity();
        array.set(count++, value);
    }

    /**
     * @precondition count != 0 - stack isn't empty
     * @postcondition pops the topmost value from the stack and returns it to the caller
     */
    public T pop() {
        if (count == 0) throw new NoSuchElementException("Stack is empty");
        return array.get(--count);
    }
    /**
     * @precondition count != 0 - stack isn't empty
     * @postcondition returns the topmost value from the stack without removing it
     */
    public T peek() {
        if (count == 0) throw new NoSuchElementException("Stack is empty");
        return array.get(count - 1);
    }

    /*
     * Implementation notes: expandCapacity
     * ------------------------------------
     * The expandCapacity method allocates a new array of twice the previous
     * size, copies the old elements to the new array, and then replaces the
     * old array with the new one.
     */

    /**
     * @postcondition creates a new stack with increased capacity and assigns the values from the old stack to the new stack
     */
    private void expandCapacity() {
        capacity *= 2;
        ArrayList<T> newArray = new ArrayList<>(capacity);
        for (int i = 0; i < count; i++) {
            newArray.set(i, array.get(i));
        }
        array = newArray;
    }

    /* Constants */

    private static final int INITIAL_CAPACITY = 10;

    /*
     * Private instance variables
     * --------------------------
     * The elements in a Stack are stored in a GenericArray, which is necessary
     * to get around Java's prohibition on arrays of a generic type.
     */

    private ArrayList<T> array;  /* Array of elements in the stack   */
    private int capacity;           /* Allocated capacity of the array  */
    private int count;              /* Actual number of elements in use */

}