
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import javax.swing.border.*;

import  java.io.*;

public class NetworkMain extends JFrame
{
	public static void main(String [] args){
	
		int numNodes = 10;  //default to localhost
		try { numNodes= Integer.parseInt(args[0] ); } catch (Exception e) {;}
		NetworkPanel netPanel = new NetworkPanel( numNodes );
			
		Toolkit toolkit = Toolkit.getDefaultToolkit();  
		Dimension screenSize = toolkit.getScreenSize();
			
		JFrame frame = new JFrame("Network");
		frame.setContentPane(netPanel.createContentPane());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//com.sun.awt.AWTUtilities.setWindowOpacity(frame, 0.70f);
		frame.setSize(500,700);
			
		//place frame in middle of screen
		int x = (screenSize.width - frame.getWidth()) / 2;  
		int y = (screenSize.height - frame.getHeight()) / 2;  
	 	frame.setLocation(x, y); 
		//frame.setUndecorated(true);
		//com.sun.awt.AWTUtilities.setWindowOpaque(frame, false);

		frame.setVisible(true);
		}
}

class NetworkPanel extends JPanel implements ActionListener
{
	
	JPanel panel = new JPanel();
	JButton start = new JButton("Make Network");
	JTextField textField = new JTextField(20);

	JButton objectButton = new JButton("Create moving Object");
	int numNodes;
	Network n;
	DrawNetwork nodePanel;
	
	public NetworkPanel(int x)
	{
		numNodes = x;
	}
	
	public JPanel createContentPane()
	{
		Border border1 = new LineBorder(Color.BLACK, 3);
		
		panel.setLayout(null);
		panel.setSize(500, 600);
		panel.setLocation(200, 0);
		panel.setBackground(Color.white);
		
		
		
		textField.addActionListener(this);
		textField.setLocation( 10, 10);
		textField.setVisible(true);
		panel.add(textField);
		
		JPanel buttonPanel = new JPanel();     
       	buttonPanel.setLayout(null);
       	buttonPanel.setLocation(000, 000);
      	buttonPanel.setSize(500,100);
		buttonPanel.setBackground(Color.white);	
		
		start.addActionListener(this);
		start.setBackground(Color.white);
		start.setLocation(10,10);
		start.setSize(175, 25);
		start.setVisible(true);
		buttonPanel.add(start);
		
		objectButton.addActionListener(this);
		objectButton.setBackground(Color.white);
		objectButton.setLocation(300,10);
		objectButton.setEnabled(false);
		objectButton.setSize(175, 25);
		objectButton.setVisible(true);
		buttonPanel.add(objectButton);
		
		panel.add(buttonPanel);
				
		return panel;
	}
	
	//this doesn't work yet, i am working on it.
	public void actionPerformed(ActionEvent evt)
	{
		Object source = evt.getSource();
		
		
		if(source == start){
			String text = textField.getText();
			addNetwork();
		}
		
		if(source == objectButton){
			objectButton.setEnabled(false);
			start.setEnabled(false);
			Ball b = new Ball(nodePanel, n);
			b.start();
		}
	}
	
	public void addNetwork()
	{
		n  = new Network( numNodes );
		nodePanel = n.getDrawNetwork();
		nodePanel.setVisible(true);
		panel.add(nodePanel);
		nodePanel.repaint();	
		objectButton.setEnabled(true);
	}
	
}


