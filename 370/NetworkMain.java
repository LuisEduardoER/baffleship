
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

import  java.io.*;


public class NetworkMain extends JFrame implements ActionListener
{
	private JPanel canvas;
	DrawNetwork d;
	JButton start = new JButton("start");
	JButton go = new JButton("go");
	
	public NetworkMain()
	{
		
		super("Node");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(1000,1000);	
		Network n = new Network( 5 );
		d = n.getDrawNetwork();
		
		/*
		JPanel buttonPanel = new JPanel();     
       	buttonPanel.setLayout(null);
       	buttonPanel.setLocation(000, 000);
      	buttonPanel.setSize(200,200);
  	
		
		start.addActionListener(this);
		start.setLocation(10,10);
		start.setSize(100, 25);
		start.setVisible(true);
		buttonPanel.add(start);
		
		go.addActionListener(this);
		go.setLocation(10,10);
		go.setSize(100, 25);
		go.setVisible(false);
		buttonPanel.add(go);
		
		add(buttonPanel);
		*/
		add(d);
		setVisible(true);
		n.alterNetwork();

	}
	
	//this doesn't work yet, i am working on it.
	public void actionPerformed(ActionEvent evt)
	{
		/*
		Object source = evt.getSource();
		if(source == start){
			start.setVisible(false);
			go.setVisible(true);
			Network n = new Network( 5 );
			network = n;
			DrawNetwork d = n.getDrawNetwork();
			add(d);
			//n.alterNetwork();
		}
		
		if(source == go){
			network.alterNetwork();
		}
		*/
	}
	
	public static void main(String [] args){
		NetworkMain frame = new NetworkMain();
	}
}


