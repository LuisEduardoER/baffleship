
//Piece.java

import java.util.*;
import java.awt.*;

public abstract class Piece
{
	//a piece is a list of blocks
	protected java.util.List<MoveableBlock> blocks = new ArrayList<MoveableBlock>();

	protected Direction facing=Direction.NORTH;

	//return a COPY of the blocks list
	public java.util.List<MoveableBlock> getBlocks()
	{
		java.util.List<MoveableBlock> blocksCopy = new ArrayList<MoveableBlock>();
		for(MoveableBlock b : blocks ) blocksCopy.add( new MoveableBlock(b) );
		return blocksCopy;
	}
	
	//each piece will have to rotate in its own way
	public abstract void RotateClockwise();
	public abstract void RotateCounterClockwise();

	//moving pieces is simple enough to do here, though
	public void Move(Direction dir) { Move(dir , 1); } 
	public void Move(Direction dir, int distance)
	{
		for(MoveableBlock b : blocks) b.Move(dir,distance); 
	}
	
}
