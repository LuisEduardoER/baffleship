
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

public class Network
{
	public java.util.List<Node> nodes = new ArrayList<Node>();

	private java.util.List<Node> nodesToAwaken = new ArrayList<Node>();

	private Point2D objectLocation;
	private Point2D previousLocation;
	
	private DrawNetwork d;

	//creates a random network of nodes
	//currently just creates 20, this could be a
	//command line argument or user choice
	Network(int numNodes){
		Random generator = new Random();
		int randX = 0;;
		int randY = 0;;	
		Node node;
		for(int i = 1; i <= numNodes; i++)
		{
			randX = generator.nextInt( 800 ) + 100;    
			randY = generator.nextInt( 800 ) + 100;  
			
			String name = "Node "+ i;
			
			Point p = new Point (randX, randY);
			//if there is an node in range
			if(closestInRange(p) != null){
				while (closestInRange(p).distance(p) < 50){
					randX = generator.nextInt( 999 ) + 100;    
					randY = generator.nextInt( 999 ) + 100;   
					p = new Point (randX, randY);
				}
			}
			addNode(name, p);		
		}	
		d = new DrawNetwork(this);
	}

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
	 
	public DrawNetwork getDrawNetwork()
	{
		return d;
	}
	 
	//goes through every node in the network
	//setting current then non current.
	public void alterNetwork()
	{
		for(Node node :nodes)
		{
			closestInRange(node.location).setCurrent();
			d.repaint();

			try{Thread.sleep(1000);}
			catch(Exception e){}

			closestInRange(node.location).setNonCurrent();

		}
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
