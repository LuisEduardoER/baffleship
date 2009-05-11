import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.border.*;

public class DrawNetwork extends JPanel
{
		
	Network network;
	Border border1 = new LineBorder(Color.BLACK, 3);

	DrawNetwork(Network n){
		this.setSize(500, 600);
		//this.setBorder(border1);
		this.setBackground(Color.white);
		this.setLocation(000,100);
		this.setVisible(false);
        network = n;
	}

	public void paintComponent(Graphics comp)
	{
		super.paintComponent(comp);
		Graphics2D comp2D = (Graphics2D)comp;
		for(Node n : network.nodes){
		    if(n.isAwake()) comp2D.setPaint(Color.red);
		    else  comp2D.setPaint(Color.black);
		    if(n.isCurrent()) comp2D.setPaint(Color.green);
		    if(!n.isAlive()) comp2D.setPaint(Color.gray);
		    comp2D.fill(Circle(n.location.getX(),n.location.getY(), 17));
			comp2D.draw(Circle(n.location.getX(),n.location.getY(), 100));
		}		
	}

	public static Ellipse2D.Double Circle(double x,double y,int r)
	{
		return new Ellipse2D.Double(x-r/2,y-r/2,r,r);
		
	}
	


}
