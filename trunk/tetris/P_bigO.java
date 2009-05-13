
//P_bigO.java

import java.awt.*;

public class P_bigO extends Piece 
{

/*
		0 		1 		2


		3 		4		5

		
		6		7		8

*/

	

	P_bigO(Point p)
	{
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.YELLOW) );
		p.translate(1,0); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.YELLOW) );
		p.translate(1,0); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.YELLOW) );
		p.translate(-2,1); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.YELLOW) );
		p.translate(1,0); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.RED) );
		p.translate(1,0); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.YELLOW) );
		p.translate(-2,1); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.YELLOW) );
		p.translate(1,0); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.YELLOW) );
		p.translate(1,0); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.YELLOW) );
	}


	public void RotateClockwise()
	{
		//This guy is too heavy to rotate

		//		for(MoveableBlock b : blocks) b.RotateClockwise() ;
		//		facing = facing.Clockwise();		
	}


	public void RotateCounterClockwise()
	{
	
	}



}
