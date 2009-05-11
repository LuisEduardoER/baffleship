
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
	
		NetworkPanel netPanel = new NetworkPanel();
			
		Toolkit toolkit = Toolkit.getDefaultToolkit();  
		Dimension screenSize = toolkit.getScreenSize();
			
		JFrame frame = new JFrame("Network");
		frame.setContentPane(netPanel.createContentPane());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,750);

		
			
		//place frame in middle of screen
		int x = (screenSize.width - frame.getWidth()) / 2;  
		int y = (screenSize.height - frame.getHeight()) / 2;  
	 	frame.setLocation(x, y); 


		frame.setVisible(true);
		}
}

class NetworkPanel extends JPanel implements ActionListener
{
	
	JPanel panel = new JPanel();
	JButton start = new JButton("Make Network");
String destinationString = "Destination";
		String routeString = "Route";
		String nbrString = "ALL_NBR";
	JRadioButton destination = new JRadioButton(destinationString);
	JRadioButton route = new JRadioButton(routeString);
	JRadioButton all_nbr = new JRadioButton(nbrString);
	JTextField textField = new JTextField(3);

	JButton objectButton = new JButton("Create moving Object");
	int numNodes;
	Network n;

	DrawNetwork nodePanel;

	String heuristic;
	
	//might use constructor for something later
	public NetworkPanel()
	{
	}
	

	public JPanel createContentPane()
	{
		Border border1 = new LineBorder(Color.BLACK, 3);

		
		panel.setSize(500, 700);
		panel.setLocation(200, 0);
		panel.setBackground(Color.white);	
		
		
		JPanel buttonPanel = new JPanel();     

       		buttonPanel.setLayout(new GridLayout(1,3, 5, 5));
       		buttonPanel.setLocation(000, 000);
      		buttonPanel.setSize(500,100);
		buttonPanel.setBackground(Color.white);	
		
		textField.addActionListener(this);
		//textField.setLocation( 200, 100);
		textField.setText("number of nodes (1-50)");
		textField.setVisible(true);
		textField.setHorizontalAlignment(JTextField.CENTER); 
		textField.requestFocus();
		buttonPanel.add(textField);
		
		start.addActionListener(this);
		start.setBackground(Color.white);
		//start.setLocation(10,10);
		start.setSize(175, 25);
		start.setVisible(true);
		buttonPanel.add(start);
		
		objectButton.addActionListener(this);
		objectButton.setBackground(Color.white);
		//objectButton.setLocation(300,10);
		objectButton.setEnabled(false);
		objectButton.setSize(175, 25);
		objectButton.setVisible(true);
		buttonPanel.add(objectButton);

		//Create the radio buttons.
	    
		    destination.setMnemonic(KeyEvent.VK_B);
		    destination.setActionCommand(destinationString);
		    destination.setSelected(false);

		   
		    route.setMnemonic(KeyEvent.VK_C);
		    route.setActionCommand(routeString);

		    
		    all_nbr.setMnemonic(KeyEvent.VK_D);
		    all_nbr.setActionCommand(nbrString);

		 //Group the radio buttons.
		    ButtonGroup group = new ButtonGroup();
		    group.add(destination);
		    group.add(route);
		    group.add(all_nbr);

		destination.addActionListener(this);
		route.addActionListener(this);
		all_nbr.addActionListener(this);

		JPanel radioPanel = new JPanel(new GridLayout(1, 0));
		radioPanel.add(destination);
		radioPanel.add(route);
		radioPanel.add(all_nbr);
		
		panel.add(radioPanel);
		panel.add(buttonPanel);
				
		return panel;
	}
	
	//this doesn't work yet, i am working on it.
	public void actionPerformed(ActionEvent evt)
	{
		Object source = evt.getSource();

		//System.out.println(evt.getActionCommand() );
		if(evt.getActionCommand() == "Destination" || evt.getActionCommand() == "Route" || evt.getActionCommand() == "ALL_NBR")
		{
			heuristic = evt.getActionCommand();
			//n.setHeuristic(heuristic);
			System.out.println(heuristic);
		}
		
		if(source == start){
			int num = 0;
			String text = textField.getText();
			if(text.length() > 2) num = 0;
			num = Integer.parseInt(text);
			if(num > 50 || num < 1) num = 0;
			addNetwork(num);
		}

		/*
		if(source == textField){
			String text = textField.getText();
			addNetwork();
		}
		*/
		if(source == objectButton){
			objectButton.setEnabled(false);
			start.setEnabled(false);
			//if(evt.getActionCommand() == destination) System.out.println("destination");
			Ball b = new Ball(nodePanel, n);
			b.start();
		}
	}
	
	public void addNetwork(int x)
	{
		n  = new Network( x);
		n.setHeuristic(heuristic);
		nodePanel = n.getDrawNetwork();
		nodePanel.setVisible(true);
		panel.add(nodePanel);
		nodePanel.repaint();	
		objectButton.setEnabled(true);
	}
	
}


