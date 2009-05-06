
//Ship.java

import java.awt.*;
import java.util.*;


public class Ship
{
	public final SquareType shipType;   //enum, shouldn't be water
	public final Point startLocation;  //where the start of the ship is
	public final Direction facing;    //the direction the ship points
	private java.util.List<PositionedSquare> squares = new ArrayList<PositionedSquare>();	


public Ship(SquareType s, Point p, Direction d)
	{
		shipType=s;
		startLocation=p;
		facing=d;
		
		for (int i=0; i<shipType.length; i++)
			squares.add(new PositionedSquare( shipType, d, Direction.Move(p,d,i) ));

		System.out.println("creating ship " +s + " at "+p+" facing "+d);

	}

public boolean collides(Point p)
	{
		for (int i=0; i< squares.size() ; i++) if ( squares.get(i).location.equals( p ) ) return true;
		return false;
	}


public boolean outOfBounds(int xMin, int yMin, int xMax, int yMax)
	{
		Point tempPoint;
		for (int i=0; i<shipType.length; i++)
		{
			tempPoint = Direction.Move(startLocation,facing,i);
			if ( (tempPoint.getX() < xMin) || (tempPoint.getX() > xMax) ||
			     (tempPoint.getY() < yMin) || (tempPoint.getY() > yMax) ) return true;
		}

		return false;			

	}


//returns true if its a hit, false for a miss
//also marks the square hit
//if you had already hit that square, congratulations, you just hit it again.
public boolean shoot(Point p)
	{
		for (int i=0; i< squares.size() ; i++) if ( squares.get(i).location.equals( p ) )
		{			
			squares.get(i).setGuessed();
			return true;
		}
		//else
		return false;
	}	

public boolean isSunk()
	{
		for (PositionedSquare ps : squares) if (! (ps.isGuessed() )) return false;
		return true;
	}


}
