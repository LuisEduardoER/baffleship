
//Square.java

import java.awt.*;


public enum Square
{

	WATER(Color.BLUE, false), CARRIER(Color.RED, true), BSHIP(Color.Green, true),
	CRUISER(Color.WHITE, true), SUB(Color.MAGENTA, true), DESTROYER(Color.PINK, true);

	private Color picture;    	//this could be a picture of water, or part of a ship...
	private boolean ship;

	private boolean endpiece=false;  	//true if this is a ship AND one of the ends

	private Direction facing=Direction.NORTH;   	/*rotate the picture to display it properly
					   end pieces of a ship "face" the rest of the ship
					   vertical middle pieces can face NORTH or SOUTH (same thing)
					   horizontal middle pices face  EAST or WEST (same thing)
					   water faces NORTH, but its doesn't matter much */			

	private boolean guessed=false;  //true if square has been shot at, false otherwise

	//constructor
	Square(Color p, boolean s) {  picture = p; ship = s;}

	//setters and getters
	public Color getPicture	() { return picture; }

	public boolean isShip() { return ship; }
	public boolean isWater() { return !ship; }

	public void setEnd(boolean e) { endpiece=g; }
	public void setEnd() { endpiece=true; }
	public boolean isEnd() { return endpiece; }




	public void setGuessed(boolean g) { guessed=g; }
	public void setGuessed() { guessed=true; }
	public boolean isGuessed() { return guessed; }

	public void setFacing( Direction d) { facing=d; }
	public Direction getFacing() { return facing; }

	public void setPicture( Color c) { picture = c; }
	public Color getPicture() { return picture; }

}
