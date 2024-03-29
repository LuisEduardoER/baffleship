

//Direction.java

/* Example usages:

	Direction a = Direction.NORTH;
	Direction b = a.Clockwise();
	b = b.Opposite();
	if ( Direction.parseDirection("East") == Direction.EAST) { ; };

*/
 

import java.awt.Point;



public enum Direction
{
	NORTH, EAST, SOUTH, WEST;
	
 	public Direction Clockwise() { return intToDir( this.ordinal() +1 ); }	
  	public Direction CounterClockwise() { return intToDir( this.ordinal() +3 ); }	
 	public Direction Opposite() { return intToDir( this.ordinal() +2 ); }	

	public static Direction intToDir(int i)
	{
		switch (i%4)
		{
			case 0: return NORTH;
			case 1: return EAST;
			case 2: return SOUTH;
			case 3: return WEST;
			default: return WEST; //needed to compile but should never happen
		}
	}
	

	public static Direction parseDirection(String s)
	{
		try { return valueOf(s.toUpperCase() ); } 
			catch (Exception e) { return null; }
        }


	public static Point Move(Point p, Direction dir, int distance)
	{
		Point newP = new Point(p);

		switch (dir)
		{
			case NORTH: newP.translate(0, - distance); break;
			case EAST:  newP.translate(distance, 0); break;
			case SOUTH: newP.translate(0, distance); break;
			case WEST:  newP.translate(- distance, 0); break;
		}

		return newP;
	}

}


