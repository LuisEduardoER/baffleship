
//P_Z.java

import java.awt.*;

public class P_Z extends Piece 
{

/*
		0

	
	2	1


	3

*/


	P_Z(Point p)
	{
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.RED) );
		p.translate(0,1); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.RED) );
		p.translate(-1,0); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.RED) );
		p.translate(0,1); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.RED) );	

	}


	public void RotateClockwise()
	{
	
		//move block 0
		blocks.get(0).Move( facing.Clockwise());
		blocks.get(0).Move( facing.Opposite());

		//block 1 
	

		//block 2 
		blocks.get(2).Move( facing);
		blocks.get(2).Move( facing.Clockwise());

		//move block 3
		blocks.get(3).Move( facing ,2 );
		
		//rotate each block inplace
		for(MoveableBlock b : blocks) b.RotateClockwise() ;

		//roate the piece itself also
		facing = facing.Clockwise();		
	}


	public void RotateCounterClockwise()
	{	
		//move block 0
		blocks.get(0).Move( facing.CounterClockwise() );
		blocks.get(0).Move( facing.Opposite() );

		//block 1 doesnt move

		//move block 2
		blocks.get(2).Move( facing.Clockwise() );
		blocks.get(2).Move( facing.Opposite());

		//move block 3
		blocks.get(3).Move( facing.Clockwise() ,2);
		
		//rotate each block inplace
		for(MoveableBlock b : blocks) b.RotateCounterClockwise() ;

		//roate the piece itself also
		facing = facing.CounterClockwise();		
	}



}
