
import java.awt.geom.Point2D.Float;

public class Node
{
	private boolean awake;
	private final Point2D.Float location;
	private float energy;

	static final float detectionRange = 5;
	static final float maxEnergy = 100;

	Node (Point2D.Float p)
	{
		awake=false;
		energy = maxEnergy;
		location = new Point2D.Float(p);
	}

	public void wakeup() { awake=true; }


	public void run()
	{
		if ( awake && (energy > 0) )
		{ 
			//blah blah blah
		}
	}

	



}
