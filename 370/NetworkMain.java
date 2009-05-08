
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

import  java.io.*;


public class NetworkMain extends JFrame
{
	Network n = new Network();
	DrawNetwork d;
	Random generator = new Random();
	NetworkMain()
	{
		super("Node");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	setSize(1000,1000);
       		createNetwork();
		add(d);
	        setVisible(true);
	        alterNetwork();
	}
	
	public void createNetwork()
	{	
		int randX;
		int randY;	
		Node node;
		for(int i = 1; i <= 20; i++)
		{
			randX = generator.nextInt( 999 );    
			randY = generator.nextInt( 999 );   
			String name = "Node "+ i;
			Point p = new Point (randX, randY);
			while (n.closestInRange(p).distance(p) < 50){
				randX = generator.nextInt( 999 );    
				randY = generator.nextInt( 999 );   
				p = new Point (randX, randY);
			}
			n.addNode(new Node( name, n, p ));
		}
		
		d = new DrawNetwork(n);
   	 }
    
    	public void alterNetwork()
	{
		for(Node node : n.nodes)
		{
			n.closestInRange(node.location).setCurrent();
			d.repaint();

			try{Thread.sleep(1000);}
			catch(Exception e){}

			n.closestInRange(node.location).setNonCurrent();

		}
	}
		
	


	public static void main(String [] args)
	{
		NetworkMain n = new NetworkMain();
	}


}
