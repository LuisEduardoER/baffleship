
//P_O.java

import java.awt.*;

public class P_O extends Piece 
{

/*
		0 		1 


		3 		2 

*/

	

	P_O(Point p)
	{
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.YELLOW) );
		p.translate(1,0); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.YELLOW) );
		p.translate(0,1); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.YELLOW) );
		p.translate(-1,0); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.YELLOW) );

	}

	public void RotateClockwise()
	{

		Direction f = facing.Clockwise();
		for(MoveableBlock b : blocks)
		{
			b.Move(f);
			f = f.Clockwise();
		}
		
		//rotate each block inplace
		for(MoveableBlock b : blocks) b.RotateClockwise() ;

		//roate the piece itself also
		facing = facing.Clockwise();		
	}


	public void RotateCounterClockwise()
	{

		Direction f = facing.Opposite();
		for(MoveableBlock b : blocks)
		{
			b.Move(f);
			f = f.Clockwise();
		}
		
		//rotate each block inplace
		for(MoveableBlock b : blocks) b.RotateCounterClockwise() ;

		//roate the piece itself also
		facing = facing.CounterClockwise();		
	}



}
