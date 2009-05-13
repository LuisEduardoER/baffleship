
//P_bigI.java

import java.awt.*;

public class P_bigI extends Piece 
{

/*
				0


		 		1


				2

				
				3


				4


				5


				6
*/


	P_bigI(Point p)
	{
		for (int i=0;i<7;i++) 
		{
			blocks.add( new MoveableBlock(p,Direction.NORTH,Color.CYAN) );
			p.translate(0,1); 
		}
	}


	public void RotateClockwise()
	{
	
		blocks.get(0).Move( facing.Clockwise(), 3 );
		blocks.get(0).Move( facing.Opposite(), 3);

		blocks.get(1).Move( facing.Clockwise(), 2 );
		blocks.get(1).Move( facing.Opposite(), 2);

		blocks.get(2).Move( facing.Clockwise() );
		blocks.get(2).Move( facing.Opposite() );

		//block 3 doesn't move

		blocks.get(4).Move( facing );
		blocks.get(4).Move( facing.CounterClockwise() );

		blocks.get(5).Move( facing, 2 );
		blocks.get(5).Move( facing.CounterClockwise(), 2 );

		blocks.get(6).Move( facing, 3 );
		blocks.get(6).Move( facing.CounterClockwise(), 3 );

		
		//rotate each block inplace
		for(MoveableBlock b : blocks) b.RotateClockwise() ;

		//roate the piece itself also
		facing = facing.Clockwise();		
	}


	public void RotateCounterClockwise()
	{	
		blocks.get(0).Move( facing.CounterClockwise(), 3 );
		blocks.get(0).Move( facing.Opposite(), 3);

		blocks.get(1).Move( facing.CounterClockwise(), 2 );
		blocks.get(1).Move( facing.Opposite(), 2);

		blocks.get(2).Move( facing.CounterClockwise() );
		blocks.get(2).Move( facing.Opposite() );

		//block 3 doesn't move

		blocks.get(4).Move( facing );
		blocks.get(4).Move( facing.Clockwise() );

		blocks.get(5).Move( facing, 2 );
		blocks.get(5).Move( facing.Clockwise(), 2 );

		blocks.get(6).Move( facing, 3 );
		blocks.get(6).Move( facing.Clockwise(), 3 );
		
		//rotate each block inplace
		for(MoveableBlock b : blocks) b.RotateCounterClockwise() ;

		//roate the piece itself also
		facing = facing.CounterClockwise();		
	}



}
