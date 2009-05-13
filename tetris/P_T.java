
//P_T.java

import java.awt.*;

public class P_T extends Piece 
{

/*

		3		0 		1 


		 		2 

*/



	P_T(Point p)
	{
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.MAGENTA) );
		p.translate(1,0); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.MAGENTA) );
		p.translate(-1,1); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.MAGENTA) );
		p.translate(-1,-1); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.MAGENTA) );

	}
	

	public void RotateClockwise()
	{
		//move blocks 1,2,3
		//block 0 doesnt move
		Direction f = facing.Opposite();
		for(int i=1; i<4; i++)
		{
			blocks.get(i).Move(f);
			f=f.Clockwise();
			blocks.get(i).Move(f);
		}				
		
		//rotate each block inplace
		for(MoveableBlock b : blocks) b.RotateClockwise() ;

		//roate the piece itself also
		facing = facing.Clockwise();		
	}


	public void RotateCounterClockwise()
	{

		//move blocks 1,2,3
		//block 0 doesnt move
		Direction f = facing.CounterClockwise();
		for(int i=1; i<4; i++)
		{
			blocks.get(i).Move(f);
			f=f.Clockwise();
			blocks.get(i).Move(f);
		}
		
		//rotate each block inplace
		for(MoveableBlock b : blocks) b.RotateCounterClockwise() ;

		//roate the piece itself also
		facing = facing.CounterClockwise();		
	}



}
