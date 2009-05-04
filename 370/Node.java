
import java.awt.geom.*;

public class Node
{
	private boolean awake;
	private Network network;
	public final Point2D location;
	private float energy;

	static final float detectionRange = 5;
	static final float maxEnergy = 100;

	Node (Point2D p)
	{
		awake=false;
		energy = maxEnergy;
		location = new Point2D.Float((float)p.getX(),(float)p.getY());
	}

	public static float distance(Point2D p, Point2D q)
	{
		return (float) Math.sqrt( Math.pow( ( p.getX() - q.getX() ),2) + Math.pow( ( p.getY() - q.getY() ),2) );
	}

	public float distance(Point2D p)
	{
		return distance(location,p);
	}

	public boolean inRange(Point2D p)
	{
		return ( detectionRange >= distance(p) );
	}

	public void wakeup() { if (energy>0) awake=true; }

	public void run()
	{
		if ( awake && (energy > 0) )
		{ 
			//blah blah blah
		}
	}

	



}
