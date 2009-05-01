
import java.awt.geom.*;

public class Node
{
	private boolean awake;
	private Network network;
	private final Point2D location;
	private float energy;


	static final float detectionRange = 5;
	static final float maxEnergy = 100;

	Node (Point2D p)
	{
		awake=false;
		energy = maxEnergy;
		location = new Point2D.Float((float)p.getX(),(float)p.getY());
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
