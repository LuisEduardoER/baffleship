
import java.awt.Point;

public class Node
{
	private boolean awake;
	private final Point location;
	private float energy;

	static final float detectionRange = 5;
	static final float maxEnergy = 100;

	Node (Point p)
	{
		awake=false;
		energy = maxEnergy;
		location = new Point(p);
	}

	public void run()
	{
		if ( awake && (energy > 0) )
		{ //blah blah blah
		}
	}




}
