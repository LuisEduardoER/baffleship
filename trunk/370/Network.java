
import java.awt.geom.*;
import java.util.ArrayList;

public class Network
{
	public java.util.List<Node> nodes = new ArrayList<Node>();

	private java.util.List<Node> nodesToAwaken = new ArrayList<Node>();

	private Point2D objectLocation;
	private Point2D previousLocation;


	public void addNode(String name, Point2D p)
	{
		nodes.add(new Node(name, this, p));
	}

	//returns a list of nodes in range of a certain point
	public ArrayList<Node> inRange(Point2D p)
	{
		java.util.ArrayList<Node> temp = new ArrayList<Node>();	
		for(Node n : nodes) if (n.inRange(p)) temp.add(n);
		return temp;
	}

	//return node closest to a given point
	public Node closestInRange(Point2D p)
	{
		Node tempNode=null;
		float closestDistance=0;	
		for(Node n : nodes)
		{
			float currentDistance= n.distance(p);
			if ( (tempNode==null) || (currentDistance<closestDistance) )
			{
				tempNode=n;			
				closestDistance=currentDistance;
			}
		}
		return tempNode;
	}

	//used by individual nodes to make list of nodes to wake up next
	public void wakeupNextTick(Node n) { nodesToAwaken.add(n); }

	//
	private void wakeupNodes()
	{
		for(Node n : nodesToAwaken) n.wakeup();
		nodesToAwaken.clear();
	}


	public void tick()
	{
		wakeupNodes();
		for(Node n : nodes) ;


	}




}
