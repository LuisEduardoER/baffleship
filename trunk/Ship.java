
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

	}

public boolean collides(Point p)
	{
		for (int i=0; i< squares.size() ; i++) if ( squares.get(i).location.equals( p ) ) return true;
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
