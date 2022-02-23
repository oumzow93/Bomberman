package agent;

public class Position {
	@Override
	public String toString() {
		return  x + "," + y;
	}

	private int  x;
	private int y;
	
	
	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
		
	public static boolean positionEqual(Position p1, Position p2) {
		return p1.x	==p2.x && p1.y==p2.y;
	}
	
	//====================================GET ET SET 
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	

}
