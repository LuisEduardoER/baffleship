
//MoveableBlock.java

import java.awt.*;

public class MoveableBlock extends Block
{
	
	private Point position;  //in our world, (0,0) is in the extreme northwest, and (1,1) is just south-east of it	  

	//there is no default constructor, all new MovableBlocks must have locations
	//MoveableBlock() { position = new Point(0,0); }

	//constructor, location only
	MoveableBlock(Point p) { position = new Point(p); }

	//constructor, full
	MoveableBlock(Point p, Direction d, Color c) { super(d,c); position = new Point(p); }

	//copy constructor
	MoveableBlock( MoveableBlock b)  { super(b); position = new Point( b.getPosition() ); }

	//constructor to turn ordinary Block into MoveableBlock
	MoveableBlock( Point p, Block b) { super(b); position = new Point(p); }

	//setters and getters
	public Point getPosition() { return new Point(position); }

	//this guy is commented out because we don't want anyone using it
	//public void setPosition( Point p) { position= new Point(p); }	

	//move the block exactly 1 unit
	public void Move(Direction dir) { Move(dir , 1); } 

	//move the block any distance
	public void Move(Direction dir, int distance)
	{
		switch (dir)
		{
			case NORTH: position.translate(0, - distance); break;
			case EAST:  position.translate(distance, 0); break;
			case SOUTH: position.translate(0, distance); break;
			case WEST:  position.translate(- distance, 0); break;
		}
	}


}
