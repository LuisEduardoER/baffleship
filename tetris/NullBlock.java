
//NullBlock.java
//this is a block that represents an empty space on the game board
//handy 

import java.awt.*;

public class NullBlock extends Block
{
	
	NullBlock()
	{
		super( Direction.NORTH, new Color(0F, 0F, 0F, 0F) );  //transparent color
	}

	//null block can't change or rotate
	public void setFacing( Direction d) { ; }
	public void setPicture( Color c) { ; }
	public void RotateClockwise() { ; }	
	public void RotateCounterClockwise() { ; }
	
}
