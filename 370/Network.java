
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

public class Network// extends Thread
{
	public java.util.List<Node> nodes = new ArrayList<Node>();

	private java.util.List<Node> nodesToAwaken = new ArrayList<Node>();
	
	public DrawNetwork d;
	private int numNodes;	

	private Point objectLocation;
	private Point previousLocation;

	private int prevX =1;
	private int prevY =1;

	private Node currentNode;
	
	private String heuristic;

	//creates a random network of nodes
	//currently just creates 20, this could be a
	//command line argument or user choice
	Network(int num){
		numNodes = num;
		createNetwork(numNodes);
	}
	
	public void createNetwork(int num)
	{
		Random generator = new Random();

		addNode("Zero", new Point(60,60));
		currentNode=nodes.get(0);
		currentNode.setCurrent();

		for(int i = 1; i < num; i++)
		{

			String name = "Node "+ i;			
			Point p = new Point (generator.nextInt( 400 ) + 50, generator.nextInt( 500 ) + 50);
			//if there is an node in range
			while (closest(p).distance(p)< (Node.detectionRange /2))
				p = new Point (generator.nextInt( 400 ) + 50, generator.nextInt( 500 ) + 50);
			addNode(name, p);		
		}
		d = new DrawNetwork(this);
		heuristic="Destination";
	}

	public void setHeuristic(String h)
	{
		heuristic = h;
	}	
	
	public void addNode(String name, Point p)
	{
		nodes.add(new Node(name, this, p));
	}

	//returns a list of nodes in range of a certain point
	public ArrayList<Node> inRange(Point p)
	{
		java.util.ArrayList<Node> temp = new ArrayList<Node>();	
		for(Node n : nodes) if (n.inRange(p)) temp.add(n);
		return temp;
	}

	//return node closest to a given point
	public Node closest(Point p)
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


	//return node closest to a given point IF IT IS IN RANGE
	public Node closestInRange(Point p)
	{
		Node tempNode=null;
		float closestDistance=0;	
		for(Node n : nodes)
		{
			float currentDistance= n.distance(p);
			    if ( ( (tempNode==null) && (n.inRange(p) ) ) || (currentDistance<closestDistance) ) 
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
	

	//receives coords of "ball" and updates network if the closest
	//node to the ball is not currently active
	public void update(int x, int y)
	{
		
		wakeupNodes();

		if( heuristic.equals("Route") || heuristic.equals("ALL_NBR"))

		{
		Node tempNodeA;
		Node tempNodeB=null;
		for(int i=1;i<=Ball.time_interval;i++)
		{
			tempNodeA=closestInRange(new Point( x+(x-prevX)*i , y+(y-prevY)*i ));
			if ( (tempNodeA != null) && (tempNodeA != tempNodeB) )
			{
				nodesToAwaken.add(tempNodeA);			
				tempNodeB=tempNodeA;
			}
		}
		}
		
	
		if(heuristic.equals("ALL_NBR"))

		{
			java.util.List<Node> neighborNodes = new ArrayList<Node>();	
			for(Node n : nodes)
				for(Node m : nodesToAwaken) 
					if (  n.inRange(m.location))
					{
						neighborNodes.add(n);
						break;
					}
			nodesToAwaken.addAll(neighborNodes); //some nodes will be in twice, but thats ok
		}


		if(heuristic.equals("Destination"))
			nodesToAwaken.add(closest(new Point(x+(x-prevX)*Ball.time_interval, y+(y-prevY)*Ball.time_interval)));

		currentNode.setNonCurrent();
		currentNode=closest(new Point(x+(x-prevX)*Ball.time_interval, y+(y-prevY)*Ball.time_interval)  );
		currentNode.setCurrent();

		prevX = x;
		prevY = y;

	}
	 
	//goes through every node in the network
	//setting current then non current.
	//this function is automatically called from start()


	//actually this function isn't even used anymore but
	//i'm keeping it just because

	public void run() 
	{
		//try{
		    /*
			for(Node node :nodes)
			{
				closestInRange(node.location).setCurrent();
				d.repaint();
				sleep(500);
				closestInRange(node.location).setNonCurrent();
			}*/
			/*
			Node node = nodes.get(0);
		    Node tempNode = node;
			for(int i = 1; i <= numNodes; i++)
			{
			    node.setCurrent();
			    d.repaint();
			    sleep(500);
			    node.setNonCurrent();
			    tempNode = closestInRange(node.location);
			    System.out.println("tempNode: "+tempNode.name);
			    node = tempNode;
			}*/
			
		//}catch (InterruptedException e) {}
	}

	//used by individual nodes to make list of nodes to wake up next
	public void wakeupNextTick(Node n) { nodesToAwaken.add(n); }

	//
	private void wakeupNodes()
	{
		if (nodesToAwaken.isEmpty()) return;
		for(Node n : nodesToAwaken) n.wakeup();
		nodesToAwaken.clear();
	}


}
