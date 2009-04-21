
//Square.java

import java.awt.*;


public class Square
{
	private ShipType shipType;   //enum

	private boolean endpiece=false;  	//true if this is a ship (not water) AND one of the ends
	private Direction facing=Direction.NORTH;   	/*rotate the picture to display it properly
					   end pieces of a ship "face" the rest of the ship
					   vertical middle pieces can face NORTH or SOUTH (same thing)
					   horizontal middle pices face  EAST or WEST (same thing)
					   water faces NORTH, but its doesn't matter much */			
	private boolean guessed=false;  //true if square has been shot at, false otherwise

	//constructor
	Square() {}

	//setters and getters
	public void setPicture( Color c) { picture = c; }
	public Color getPicture() { return picture; }

	//I won't allow setters for ship


	public void setEnd(boolean e) { endpiece=e; }
	public void setEnd() { endpiece=true; }
	public boolean isEnd() { return endpiece; }

	public void setFacing( Direction d) { facing=d; }
	public Direction getFacing() { return facing; }

	public void setGuessed(boolean g) { guessed=g; }
	public void setGuessed() { guessed=true; }
	public boolean isGuessed() { return guessed; }

	
	


}
