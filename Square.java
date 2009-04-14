
//Square.java

import java.awt.*;


public class Square
{

	private boolean ship;     //true for ship, false for water
	private boolean guessed;  //true if square has been shot at, false otherwise

	private Color picture;    //this could be a picture of water, or part of a ship...
	//if its a ship picture we might want to rotate it
	//or we could make 4 pictures for each possible ship part
	private Direction facing;  

	//water constrctor
	Square() { ship=false; guessed=false; picture=Color.BLUE; facing= Direction.NORTH; }

	//ship constructor has picture and direction
	Square(Color p, Direction d) {  ship=true; guessed=false; picture = p; facing = d;}

	//copy constructor
	Square( Square s) { ship = s.isShip(); guessed= s.isGuessed(); picture = s.getPicture(); facing = s.getFacing(); }

	//setters and getters
	public void setShip(boolean s) { ship=s; }
	public void setShip() { ship=true; }
	public void setWater(boolean s) { ship=!s; }
	public void setWater() { ship=false; }
	public boolean isShip() { return ship; }
	public boolean isWater() { return !ship; }

	public void setGuessed(boolean g) { guessed=g; }
	public void setGuessed() { guessed=true; }
	public boolean isGuessed() { return guessed; }

	public void setFacing( Direction d) { facing=d; }
	public Direction getFacing() { return facing; }

	public void setPicture( Color c) { picture = c; }
	public Color getPicture() { return picture; }

}
