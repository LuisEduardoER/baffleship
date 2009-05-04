import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;

public class DrawNetwork extends JPanel
{
		
	Network network = new Network();

	DrawNetwork(){


	}

	public void paintComponent(Graphics comp)
	{
		super.paintComponent(comp);
		Graphics2D comp2D = (Graphics2D)comp;
		
		Ellipse2D.Double node = new Ellipse2D.double(0, , 50, 50);
		comp2D.setPaint(Color.red);
		comp2D.fill(node);
		
	}
}
