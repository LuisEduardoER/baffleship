
import java.awt.geom.*;
import java.util.ArrayList;

public class Network
{
	java.util.List<Node> nodes = new ArrayList<Node>();


	public ArrayList<Node> inRange(Point2D p)
	{
		java.util.ArrayList<Node> temp = new ArrayList<Node>();	
		for(Node n : nodes) if (n.inRange(p)) temp.add(n);
		return temp;
	}


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




}
