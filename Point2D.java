package prog5;

// class Point2D
// has 2 fields, x and y
// provides methods to get and set X and Y
public class Point2D {
	private int x;
	private int y;
	
	public Point2D() {
		x = 0;
		y = 0;
	}
	
	public Point2D(int x_coord, int y_coord) {
		x = x_coord;
		y = y_coord;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setCoord(int x_coord, int y_coord) {
		x = x_coord;
		y = y_coord;
	}
}
