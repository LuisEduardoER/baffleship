
import java.awt.geom.*;

public class Node
{

	static final float detectionRange = 5;
	static final float maxEnergy = 100;

	private Network network; //context
	private String name; //mostly for debugging
	private boolean awake;
	private boolean current=false;  //does this node consider itself to be the "current node"?
	public final Point2D location;
	private float energy;		//0 or less is dead

	//static: distance between two points
	public static float distance(Point2D p, Point2D q)
	{
		return (float) Math.sqrt( Math.pow( ( p.getX() - q.getX() ),2) + Math.pow( ( p.getY() - q.getY() ),2) );
	}

	//constructor
	Node (String _name, Network _network, Point2D p)
	{
		name=_name;
		network = _network;
		awake=false;
		location = new Point2D.Float((float)p.getX(),(float)p.getY());
		energy = maxEnergy;
		System.out.println("Created new node "+name+" at location "+location);
	}
		
	//distance from this node to a point
	public float distance(Point2D p)
	{
		return distance(location,p);
	}

	// true if the given point is in range of this node
	public boolean inRange(Point2D p)
	{
		return ( detectionRange >= distance(p) );
	}

	//wake self up
	public void wakeup() { if (isAlive()) { awake=true; System.out.println("Waking up node "+name+" at location "+location); } }

	
	public void setCurrent()
	{
		if (isAlive())
		{
			awake=true;
			current=true;
			System.out.println("Node "+name+" at location "+location+ " is now Current.");
		}
	}

	public void setNonCurrent()
	{
		awake=false;
		current=false;
		System.out.println("Node "+name+" at location "+location+ " is no longer Current.");
	}

	public void run()
	{
		if ( awake && isAlive() )
		{ 
;
		}
	}
	
	public boolean isAwake() { return awake; }
	public boolean isCurrent() { return current; }
	public boolean isAlive() { return (energy>0); }

}
