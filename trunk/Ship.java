
//Ship.java

import java.awt.*;

public class Ship
{
	public final SquareType shipType;   //enum, shouldn't be water
	public final Point startLocation;
	public final Direction facing;   
	public java.util.List<Square> squares = new ArrayList<Square>();	



	public Ship(SquareType s, Point p, Direction d)
	{
		shipType=s;
		startLocation=p;
		facing=d;
		
		for (int i=0; i<shipType.length; i++)
		{
			squares.add(new Square( shipType, Direction d));




	;}




}
