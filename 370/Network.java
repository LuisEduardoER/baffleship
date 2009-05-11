
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
	
	String heuristic;

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
		int randX = 0;;
		int randY = 0;;	
		Node node;
		for(int i = 1; i <= num; i++)
		{
			randX = generator.nextInt( 400 ) + 50;    
			randY = generator.nextInt( 500 ) + 50;  
			
			String name = "Node "+ i;
			
			Point p = new Point (randX, randY);
			//if there is an node in range
			if(closest(p) != null){
				while (closest(p).distance(p) < 50){
					randX = generator.nextInt( 400 ) + 50;    
					randY = generator.nextInt( 500 ) + 50;   
					p = new Point (randX, randY);
				}
			}
			addNode(name, p);		
		}
		d = new DrawNetwork(this);
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

		if(heuristic.equals("Route") )
{
		Node tempNodeA;
		Node tempNodeB=null;
		for(int i=1;i<101;i++)
		{
			tempNodeA=closestInRange(new Point( x+(x-prevX)*i , y+(y-prevY)*i ));
			if ( (tempNodeA != null) && (tempNodeA != tempNodeB) )
			{
				nodesToAwaken.add(tempNodeA);			
				tempNodeB=tempNodeA;
			}
		}


}


//		Point p = new Point(x, y);

		//if(heuristic.equals("Destination")) predictedNode7.wakeup();
		

		{

		}

/*		if(!closestNode.isCurrent()){
			for(Node node :nodes){
				node.setNonCurrent();
			}
			closestNode.setCurrent();
			//d.repaint();
		}*/
		prevX = x;
		prevY = y;
		d.repaint();
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
