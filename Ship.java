
//Ship.java

import java.awt.*;

import java.util.*;


public class Ship
{
	public final SquareType shipType;   //enum, shouldn't be water
	public final Point startLocation;  //where the start of the ship is
	public final Direction facing;    //the direction the ship points
	public java.util.List<PositionedSquare> squares = new ArrayList<PositionedSquare>();	


public Ship(SquareType s, Point p, Direction d)
	{
		shipType=s;
		startLocation=p;
		facing=d;
		
		for (int i=0; i<shipType.length; i++)
			squares.add(new PositionedSquare( shipType, d, Direction.Move(p,d,i) ));

	}




}
