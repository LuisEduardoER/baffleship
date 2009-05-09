
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
	
		int numNodes = 0;  //default to localhost
		try { numNodes= Integer.parseInt(args[0] ); } catch (Exception e) {;}
		NetworkPanel netPanel = new NetworkPanel( numNodes );
			
		Toolkit toolkit = Toolkit.getDefaultToolkit();  
		Dimension screenSize = toolkit.getScreenSize();
			
		JFrame frame = new JFrame("Network");
		frame.setContentPane(netPanel.createContentPane());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//com.sun.awt.AWTUtilities.setWindowOpacity(frame, 0.70f);
		frame.setSize(1000,1000);
			
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

	JButton go = new JButton("Animate");
	int numNodes;
	Network n;
	DrawNetwork nodePanel;
	
	public NetworkPanel(int x)
	{
		numNodes = x;
	}
	
	public JPanel createContentPane()
	{
		panel.setLayout(null);
		panel.setSize(500, 500);
		panel.setLocation(200, 0);
		panel.setBackground(Color.white);
		
		Border border1 = new LineBorder(Color.BLACK, 3);
		
		textField.addActionListener(this);
		textField.setLocation( 10, 10);
		textField.setVisible(true);
		panel.add(textField);
		
		JPanel buttonPanel = new JPanel();     
       	buttonPanel.setLayout(null);
		buttonPanel.setBorder(border1);
       	buttonPanel.setLocation(000, 000);
      	buttonPanel.setSize(200,900);
		buttonPanel.setBackground(Color.white);	
		
		start.addActionListener(this);
		start.setBackground(Color.white);
		start.setLocation(10,80);
		start.setSize(150, 25);
		start.setVisible(true);
		buttonPanel.add(start);
		
		go.addActionListener(this);
		go.setLocation(10,150);
		go.setBackground(Color.white);
		go.setEnabled(false);
		go.setSize(150, 25);
		go.setVisible(true);
		buttonPanel.add(go);
		
		panel.add(buttonPanel);
		
		
		return panel;
	}
	
	//this doesn't work yet, i am working on it.
	public void actionPerformed(ActionEvent evt)
	{
		Object source = evt.getSource();
		
		
		if(source == start){
			String text = textField.getText();
			//start.setEnabled(false);
			go.setEnabled(true);
			addNetwork();
		}
		
		if(source == go){
			n.start();
		}	
	}
	
	public void addNetwork()
	{
		n  = new Network( numNodes );
		nodePanel = n.getDrawNetwork();
		nodePanel.setVisible(true);
		panel.add(nodePanel);
		nodePanel.repaint();	
	}
	
}


