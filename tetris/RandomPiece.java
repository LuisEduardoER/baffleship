
//RandomPiece.java

/*
	Class does not need to be instantiated.  Example useage:

	Piece myPiece = RandomPiece.Create(new Point(1,1) );

 	According to the Tetris wiki, "random" in Tetris is not truly random.
	There is a virtual "bag" with one of each piece; they are removed randomly,
	but not replaced until the bag is empty.  This makes the game more "fair",
	but also easier

	We decided to be slightly more random; a new set of seven is added when the
	bag has only two pieces left.	

*/

import java.awt.*;
import java.util.Random;
import java.util.*;


public abstract class RandomPiece
{
	static final int numDifferentPieces = 7;  //magic tetris number, there are seven different tetrominoes
	static final int refillThreshold = 2;   //change to zero for Official Tetris behaviour
	private static Random r = new Random();

	private static java.util.List<Integer> pieceBag = new ArrayList<Integer>();  //hold pieces which have not yet been given out

	//add seven new pieces to the bag
	private static void refillBag() { for(int i=0;i<numDifferentPieces;i++) pieceBag.add(i); }
	
	//throw away all remaining pieces
	public static void Reset() { pieceBag.clear(); }

	public static Piece Create(Point p)
	{
		if (pieceBag.size() <= refillThreshold ) refillBag();
		switch( pieceBag.remove( r.nextInt( pieceBag.size() ) ) )
		{
			case 0: return new P_T(p);
			case 1: return new P_J(p);
			case 2: return new P_L(p);
			case 3: return new P_O(p);
			case 4: return new P_S(p);
			case 5: return new P_Z(p);
			case 6: return new P_I(p);		
			default: return new P_O(p); //should never happen
		}
	}


}
