package prog5;

// Class Stack
// includes functions to push, pop, check if stack is empty, and check top element of the stack
public class Stack {
	private Point2D [] array;     // dynamic array where contents of stack are stored
	private int top = -1;         // points to the top element of the stack
	private int capacity = 0;     // indicates the capacity of the array
	
	// constructor - size of array must be specified
	public Stack(int size) {
		// allocate the array
		array = new Point2D[size];
		
		// initialize array elements to null
		for(Point2D p : array) {
			p = null;
		}
		
		// set initial values for capacity and top
		capacity = size;
		top = -1;
	}
	
	// check if stack is empty
	public boolean isEmpty() {
		if (top < 0)
			return true;
		return false;
	}
	
	// doubles the capacity of the array 
	public void grow() {
		// allocate new array that has twice the original capacity
		Point2D[] tempArray = new Point2D[capacity * 2];
		
		// copy elements from old array to the new one
		for(int i = 0; i < capacity; i++) {
			tempArray[i] = array[i];
		}
		array = tempArray;
		capacity *= 2;
	}
	
	// return the top element of the stack
	public Point2D getTop() {
		// check if array is full or top is somehow larger than capacity
		if (top < 0 || top > capacity - 1) 
			return null;
		// top is valid, return the top element of the stack
		return array[top];
	}
	
	// push a position on the stack
	public void push(int x, int y) {
		// allocate a point object
		Point2D p = new Point2D(x, y);
		
		// if array is full, call grow() to double its capacity
		if (top == capacity - 1) {
			grow();
		}
		
		array[++top] = p;
	}
	
	// pop an element off the stack
	// returns the element popped
	public Point2D pop() {
		
		// check if array is empty
		if (isEmpty()) {
			return null;
		}
		
		// save the element in the variable that will be returned
		Point2D ret = array[top];
		
		// delete the element from array
		array[top--] = null;
		
		return ret;
	}
	
	public int getCapacity() {
		return capacity;
	}
}
