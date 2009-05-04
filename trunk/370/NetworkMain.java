
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

import  java.io.*;


public class NetworkMain extends JFrame
{

	DrawNetwork d = new DrawNetwork();

	NetworkMain()
	{
		super("tetris");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(500,500);
		//place frame in middle of screen
		int x = (screenSize.width - getWidth()) / 2;  
		int y = (screenSize.height - getHeight()) / 2;  

		add(d);

	
	}



	public static void main(String [] args)
	{
		NetworkMain n = new NetworkMain();
	}


}
