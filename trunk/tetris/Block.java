
//Block.java

import java.awt.*;


public class Block
{

	protected Color picture;   	//eventually this could be an image or a gradient
	protected Direction facing;    //needed to rotate image or gradient

	//default constrctor
	Block() { facing= Direction.NORTH; picture=Color.BLACK ; }

	//picture-only constructor
	Block(Color c) { facing = Direction.NORTH; picture = c; }

	//full constructor
	Block(Direction d, Color c) { facing = d; picture = c; }

	//copy constructor
	Block( Block b)	{ facing = b.getFacing(); picture = b.getPicture(); }

	//setters and getters
	public void setFacing( Direction d) { facing=d; }
	public Direction getFacing() { return facing; }
	public void setPicture( Color c) { picture = c; }
	public Color getPicture() { return picture; }

	//methods to rotate the block
	public void RotateClockwise() { facing = facing.Clockwise(); }	
	public void RotateCounterClockwise() { facing = facing.CounterClockwise(); }

}
