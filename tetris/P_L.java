
//P_L.java

import java.awt.*;

public class P_L extends Piece 
{

/*

	    	0


		1


		2		3


*/


	P_L(Point p)
	{
		Color NEW_ORANGE = new Color(200,100,20);  //the default Color.ORANGE looks too much like yellow		
		blocks.add( new MoveableBlock(p,Direction.NORTH,NEW_ORANGE) );
		p.translate(0,1); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,NEW_ORANGE) );
		p.translate(0,1); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,NEW_ORANGE) );
		p.translate(1,0); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,NEW_ORANGE) );

	}

	
	public void RotateClockwise()
	{
	
		//move block 0								
		blocks.get(0).Move( facing.Opposite() );  	
		blocks.get(0).Move( facing.Clockwise() );	

		//block 1 doesnt move

		//move block 2
		blocks.get(2).Move( facing );
		blocks.get(2).Move( facing.CounterClockwise() );
		

		//move block 3
		blocks.get(3).Move( facing.CounterClockwise() , 2 );

		
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
		blocks.get(2).Move( facing );

		//move block 3
		blocks.get(3).Move( facing , 2 );


		
		//rotate each block inplace
		for(MoveableBlock b : blocks) b.RotateCounterClockwise() ;

		//roate the piece itself also
		facing = facing.CounterClockwise();		
	}


}
