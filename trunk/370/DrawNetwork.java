import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;

public class DrawNetwork extends JPanel
{
		
	Network network;

	DrawNetwork(Network n){
        network = n;
	}

	public void paintComponent(Graphics comp)
	{
		super.paintComponent(comp);
		Graphics2D comp2D = (Graphics2D)comp;
		for(Node n : network.nodes){
		    Ellipse2D.Double node = new Ellipse2D.Double(n.location.getX(),n.location.getY(), 30, 30);
		    comp2D.setPaint(Color.red);
		    comp2D.fill(node);
		}
		
	}
}
