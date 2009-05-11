
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

public class Network// extends Thread
{
	public java.util.List<Node> nodes = new ArrayList<Node>();

	private java.util.List<Node> nodesToAwaken = new ArrayList<Node>();
	
	private DrawNetwork d;
	private int numNodes;	

	private Point objectLocation;
	private Point previousLocation;

	private int prevX =1;
	private int prevY =1;
	private float nextX1 =1;
	private float nextY1 =1;
	private float nextX2 =1;
	private float nextY2 =1;
	private float nextX3 =1;
	private float nextY3 =1;
	private float nextX4 =1;
	private float nextY4 =1;
	private float nextX5 =1;
	private float nextY5 =1;
	private float nextX6 =1;
	private float nextY6 =1;
	private float nextX7 =1;
	private float nextY7 =1;
	private float slopeX = 1;
	private float slopeY = 1;
	
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
	public Node closest(Point2D p)
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
	

	//receives coords of "ball" and updates network if the closest
	//node to the ball is not currently active
	public void update(int x, int y)
	{
/*	private float predictedDistanceX = 1;
	private float predictedDistanceY = 1;

		if(prevX != x && prevY != y) { slopeX = (x - prevX); slopeY = (y - prevY);}
		predictedDistanceX = 100*slopeX;
		predictedDistanceY = 100*slopeY;

		nextX = x+predictedDistanceX;
		nextY = y+predictedDistanceY;  */

		nextX1 = x+(x-prevX)*10;
		nextY1 = y+(y-prevY)*10; 

		nextX2 = x+(x-prevX)*20;
		nextY2 = y+(y-prevY)*20; 

		nextX3 = x+(x-prevX)*30;
		nextY3 = y+(y-prevY)*30; 

		nextX4 = x+(x-prevX)*40;
		nextY4 = y+(y-prevY)*40; 

		nextX5 = x+(x-prevX)*50;
		nextY5 = y+(y-prevY)*50; 

		nextX6 = x+(x-prevX)*60;
		nextY6 = y+(y-prevY)*60; 

		nextX7 = x+(x-prevX)*70;
		nextY7 = y+(y-prevY)*70; 

		/*
		System.out.println("predicted distance: "+"("+nextX+", "+nextY+")");
		System.out.println("("+x+", "+y+") ("+prevX+", "+prevY+")");
		System.out.println("slope of predicted path is: ("+slopeX+", "+slopeY+")");
		*/

		Point p = new Point(x, y);
		Point nextP1 = new Point((int)nextX1, (int)nextY1);
		Point nextP2 = new Point((int)nextX2, (int)nextY2);
		Point nextP3 = new Point((int)nextX3, (int)nextY3);
		Point nextP4 = new Point((int)nextX4, (int)nextY4);
		Point nextP5 = new Point((int)nextX5, (int)nextY5);
		Point nextP6 = new Point((int)nextX6, (int)nextY6);
		Point nextP7 = new Point((int)nextX7, (int)nextY7);

		Node closestNode = closest(p);
		Node predictedNode1 = closest(nextP1);
		Node predictedNode2 = closest(nextP2);
		Node predictedNode3 = closest(nextP3);
		Node predictedNode4 = closest(nextP4);
		Node predictedNode5 = closest(nextP5);
		Node predictedNode6 = closest(nextP6);
		Node predictedNode7 = closest(nextP7);

		if(heuristic.equals("Destination")) predictedNode7.wakeup();
		
		if(heuristic.equals("Route")) {
			predictedNode1.wakeup();
			predictedNode2.wakeup();
			predictedNode3.wakeup();
			predictedNode4.wakeup();
			predictedNode5.wakeup();
			predictedNode6.wakeup();
			predictedNode7.wakeup();
		}

		if(!closestNode.isCurrent()){
			for(Node node :nodes){
				node.setNonCurrent();
			}
			closestNode.setCurrent();
			//d.repaint();
		}
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
		for(Node n : nodesToAwaken) n.wakeup();
		nodesToAwaken.clear();
	}


	public void tick()
	{
		wakeupNodes();
		for(Node n : nodes) ;
	}
	
	

}
