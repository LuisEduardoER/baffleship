
//P_4dots.java

import java.awt.*;

public class P_4dots extends Piece 
{

/*

		0		_ 		1		 


		_		_		_


		3		_ 		2 


	This piece does not rotate normally --
	instead rotate functions make it spread out or shrink.

*/


	P_4dots(Point p)
	{
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.RED) );
		p.translate(2,0); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.GREEN) );
		p.translate(0,2); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.YELLOW) );
		p.translate(-2,0); 
		blocks.add( new MoveableBlock(p,Direction.NORTH,Color.BLUE) );
	}
	

	public void RotateClockwise()
	{
		//0 doesn't move
		blocks.get(1).Move(Direction.EAST);
		blocks.get(2).Move(Direction.EAST);
		blocks.get(2).Move(Direction.SOUTH);
		blocks.get(3).Move(Direction.SOUTH);
		
		//rotate each block inplace
		for(MoveableBlock b : blocks) b.RotateClockwise() ;
	}


	public void RotateCounterClockwise()
	{
		//0 doesn't move
		blocks.get(1).Move(Direction.WEST);
		blocks.get(2).Move(Direction.WEST);
		blocks.get(2).Move(Direction.NORTH);
		blocks.get(3).Move(Direction.NORTH);
		
		//rotate each block inplace
		for(MoveableBlock b : blocks) b.RotateCounterClockwise() ;
		
	}


}
