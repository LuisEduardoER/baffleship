
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
		super("tetris");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(500,500);
		/*place frame in middle of screen
		int x = (screenSize.width - getWidth()) / 2;  
		int y = (screenSize.height - getHeight()) / 2;  
        */
        addNodes();
		add(d);
        setVisible(true);
	
	}
	
	public void addNodes()
	{
	    n.addNode("Node 1", new Point2D.Float(5, 5));
        n.addNode("Node 2", new Point2D.Float(102, 143));
        n.addNode("Node 3", new Point2D.Float(25, 50)); 
        d = new DrawNetwork(n);

    }



	public static void main(String [] args)
	{
		NetworkMain n = new NetworkMain();
	}


}
