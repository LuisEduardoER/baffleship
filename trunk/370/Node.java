
import java.awt.geom.*;

public class Node
{

	static final float detectionRange = 5;
	static final float maxEnergy = 100;

	private Network network; //context
	private boolean awake;
	private boolean current;  //does this node consider itself to be the "current node"?
	public final Point2D location;
	private float energy;		//0 or less is dead

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
