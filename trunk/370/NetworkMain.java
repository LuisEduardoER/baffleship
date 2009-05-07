
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
	NetworkMain()
	{
		super("Node");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	setSize(500,500);
       		createNetwork();
		add(d);
	        setVisible(true);
	        alterNetwork();
	}
	
	public void createNetwork()
	{	    
	    	n.addNode("Node 1", new Point2D.Float(5, 5));
		n.addNode("Node 2", new Point2D.Float(50, 50));
		n.addNode("Node 3", new Point2D.Float(100, 100)); 
		n.addNode("Node 3", new Point2D.Float(200, 200));
		n.addNode("Node 4", new Point2D.Float(300, 300));
		n.addNode("Node 5", new Point2D.Float(400, 400));
		n.addNode("Node 6", new Point2D.Float(100, 300));
		n.addNode("Node 7", new Point2D.Float(300, 100));
		n.addNode("Node 8", new Point2D.Float(200, 400));
		n.addNode("Node 9", new Point2D.Float(400, 200));
		d = new DrawNetwork(n);
   	 }
    
    public void alterNetwork(){
    
        n.nodes.get(1).setCurrent();
        d.repaint();
        
        try{Thread.sleep(1000);}
        catch(Exception e){}
        
	n.nodes.get(1).setNonCurrent();
        n.nodes.get(2).setCurrent();
        d.repaint();
        
        try{Thread.sleep(1000);}
        catch(Exception e){}
        
	n.nodes.get(2).setNonCurrent();
        n.nodes.get(3).setCurrent();      
        d.repaint();
        
        try{Thread.sleep(1000);}
        catch(Exception e){}
        
	n.nodes.get(3).setNonCurrent();
        n.nodes.get(4).setCurrent();
        d.repaint();
        
        try{Thread.sleep(1000);}
        catch(Exception e){}
        
	n.nodes.get(4).setNonCurrent();
        n.nodes.get(5).setCurrent();
        d.repaint();
        
        try{Thread.sleep(1000);}
        catch(Exception e){}
    }



	public static void main(String [] args)
	{
		NetworkMain n = new NetworkMain();
	}


}
