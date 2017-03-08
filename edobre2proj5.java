/*
 * Emanuil Dobrev
 * CS 211
 * PROJ 5
 * Maze navigating algorithm using breadth first search
 * 
 */

package prog5;
import java.awt.Color;
import java.io.*;
import java.util.*;

public class edobre2proj5 {
	// possible states for a location of the board 
    public enum state {UNVISITED, VISITED, BLOCKED};
    
	public static void main(String[] args) {
		
		// get the filename from the first argument
		String filename = args[0];
		
		// local variable declarations
		int rows = 0;
		int columns = 0;
		state [][] board;
		GridDisplay disp;
		
		// open file and initialize scanner
		File f = new File(filename);
		Scanner sc = null;
		
		// attempt to open file
		// if operation was unsuccessful, throw an error and exit
		try {
			sc = new Scanner(f);
		}
		catch (FileNotFoundException fnfe) {
			System.err.println("File not found");
			return;
		}
		
		// get the number of rows and columns
		do {
		  rows = sc.nextInt();
		  columns = sc.nextInt();
		  
		  // error checking
		  if (rows < 1 || columns < 1)
			  System.err.println("invalid: maze sizes must be greater than zero");
		} while (rows < 1 || columns < 1);		
		
		// allocate and initialize the board state
		board = new state [rows + 2][columns + 2];
		// create the grid display object
		disp = new GridDisplay(rows + 2, columns + 2);
		
		for (int i = 0; i < rows + 2; i++) {
			for( int j = 0; j < columns + 2 ; j++) {
				if (i == 0 || j == 0 || i == (rows + 1) || j  == (columns + 1))
					board[i][j] = state.BLOCKED;
				else
					board[i][j] = state.UNVISITED;
			}
		}

		// get the starting position
		
		int startx = 0;
		int starty = 0;
		
		while(sc.hasNextInt()) {
			startx = sc.nextInt();
			starty = sc.nextInt();
			
			// check if start x is in the valid range
			if (startx < 1 || startx > rows) {
				System.err.printf("invalid: row %d is outside the range from 1 to %d\n"
						, startx, rows);
				continue;
			}
			
			// check if start y is in the valid range
			if (starty < 1 || starty > columns ) {
				System.err.printf("invalid: column: %d is outside the range from 1 to %d\n",
						starty,  columns);
				continue;
			}
			break;
		}
		disp.setColor(startx, starty, Color.CYAN);
				
		// get the end position
		int endx = 0;
		int endy = 0;
		
		while(true) {
			endx = sc.nextInt();
			endy = sc.nextInt();
			
			// check if endx is in the valid range
			if (endx < 1 || endx > rows) {
				System.err.printf("invalid: row %d is outside the range from 1 to %d\n"
						, endx, rows);
				continue;
			}
			
			// check if endy is in the valid range
			if (endy < 1 || endy > columns ) {
				System.err.printf("invalid: column %d is outside the range from 1 to %d\n",
						endy,  columns);
				continue;
			}
			break;
		}
		disp.setColor(endx, endy, Color.RED);
		
		// get blocked positions
		while (sc.hasNextInt() ) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			
			// check if attempting to block starting or ending position
			if (x == startx && y == starty){
				System.err.println("invalid: attempting to block starting position");
				continue;
			}
			
			if (x == endx && y == endy) {
				System.err.println("invalid: attempting to block ending position");
				continue;
			}
			
			// check if x and y are in the valid range
			if (x < 1 || x > rows) {
				System.err.printf("invalid: row %d is outside the range of 1 to %d\n",
						x, rows);
				continue;
			}
			
			if (y < 1 || y > columns) {
				System.err.printf("invalid: column %d is outside the range of 1 to %d\n",
						y, columns);
				continue;
			}
			
			// set the state of this x,y position to blocked
			board[x][y] = state.BLOCKED;
		}
		
		DisplayInitialBoard(board, disp, rows, columns);
		DepthFirstSearch(board, disp, startx, starty, endx, endy);
		// close scanner
		sc.close();
	}
	
    // puts the current thread to sleep for some number of milliseconds to allow for "animation"
    public static void mySleep( int milliseconds)
    {
      try
      {
        Thread.sleep(milliseconds);
      }
      catch (InterruptedException ie)
      {
      }
    }
    
	// function to display the initial board
	// colors blocked tiles in black
	// start tile in cyan
	// end tile in red
	// unvisited in white
	public static void DisplayInitialBoard(state[][] board, GridDisplay disp, int rows, int columns) {
		for(int i = 0; i < rows + 2; i++) {
			for (int j = 0; j < columns + 2; j++) {
				// check state of position and set the color for that state
				if(board[i][j] == state.BLOCKED) {
					disp.setColor(i, j, Color.BLACK);
				}
			}
		}
	}
	
	// perform a breadth first search on the maze
	// colors visited tiles in green, and when the search backs out of a path it will color
	// the tiles in light gray.
	public static void DepthFirstSearch(state[][] board, GridDisplay disp, int startx, int starty,
										int endx, int endy) {
		Stack dfs_stack = new Stack(4);
		Point2D p;
		int sleep_time = 100; // sleep time in milliseconds
		
		// push the starting position on the stack
		dfs_stack.push(startx, starty);
		
		// mark the starting position as visited
		board[startx][starty] = state.VISITED;
		
		// sleep for 100ms
		mySleep(sleep_time);
		
		// repeat until stack is empty or end is found
		while( !dfs_stack.isEmpty()) {
			// if top of stack equals end, then end has been found
			p = dfs_stack.getTop();
			if (p.getX() == endx && p.getY() == endy) {
				break;
			}
			
			// check if top has an unvisited and unblocked neighbor
			// push the location on the stack and mark the tile as visited
			// display the tile in green
			if (board[p.getX() + 1][p.getY()] == state.UNVISITED ) {
				dfs_stack.push(p.getX() + 1, p.getY());
				board[p.getX() + 1][p.getY()] = state.VISITED;
				disp.setColor(p.getX() + 1, p.getY(), Color.GREEN);
				mySleep(sleep_time);
			}
			else if (board[p.getX()][p.getY() + 1] == state.UNVISITED ) {
				dfs_stack.push(p.getX(), p.getY() + 1);
				board[p.getX()][p.getY() + 1] = state.VISITED;
				disp.setColor(p.getX(), p.getY() + 1, Color.GREEN);
				mySleep(sleep_time);
			}
			else if (board[p.getX()][p.getY() - 1] == state.UNVISITED) {
				dfs_stack.push(p.getX(), p.getY() - 1);
				board[p.getX()][p.getY() - 1] = state.VISITED;
				disp.setColor(p.getX(), p.getY() - 1, Color.GREEN);
				mySleep(sleep_time);
			}
			else if (board[p.getX() - 1][p.getY()] == state.UNVISITED) {
					dfs_stack.push(p.getX() - 1, p.getY());
					board[p.getX() - 1][p.getY()] = state.VISITED;
					disp.setColor(p.getX() - 1, p.getY(), Color.GREEN);
					mySleep(sleep_time);
			}


			else {
				// pop off the element
				// the algorithm is backing out so we set the color of this tile to light gray
				p = dfs_stack.pop();
				disp.setColor(p.getX(), p.getY(), Color.LIGHT_GRAY);
				mySleep(sleep_time);
			}
		}
		
		if(dfs_stack.isEmpty())
			System.out.println("No solution found");
		else {
			PrintSolution(dfs_stack);
		}
	}
	
	// print the solution - prints the coordinates of each step in (row, column) form
	public static void PrintSolution(Stack dfs_stack) {
		System.out.println("Solution found. Listing coordinates in the form (row, column) ...");
		Stack print_stack = new Stack(dfs_stack.getCapacity());
		while( ! dfs_stack.isEmpty()) {
			Point2D p = dfs_stack.pop();
			print_stack.push(p.getX(), p.getY());
		}
		
		while( ! print_stack.isEmpty()) {
			Point2D p = print_stack.pop();
			System.out.printf("(%d, %d)\n", p.getX(), p.getY());
		}
	}
}
