//john gaetano

import java.awt.Point;

class PositionedSquare extends Square
{
        public final Point location;

	//constructor
	PositionedSquare( SquareType st, Direction d, Point p) { super(st,d); location=new Point(p); }

}
