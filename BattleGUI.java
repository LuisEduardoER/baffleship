import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import javax.swing.border.*;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;

import  java.io.*;


class BattleGUI extends JFrame implements ActionListener
{	
	JFrame application = new JFrame();
	

        JButton button1 = new JButton("Ready");
        JButton button2 = new JButton("Button");
        JButton button3 = new JButton("Button");
        JButton button4 = new JButton("Button");
	JTextField usernameField = new JTextField(20);
	JTextField passwordField = new JTextField(20);
 

	JButton buttonStartOver = new JButton("Restart");

	JButton buttonArray[][] = new JButton[10][10];

	JButton buttonArray2[][] = new JButton[10][10];

	JTextField enterField; // enters information from user
   	JTextArea displayArea; // display information to user

	Client client;

	String delims = "[ ]+";
	
		//==========================================ICONS===============
		ImageIcon carrierIcon = createImageIcon("carrier.jpg");
		ImageIcon bshipIcon = createImageIcon("battleship.jpg");
		ImageIcon cruiserIcon = createImageIcon("cruiser.jpg");
		ImageIcon subIcon = createImageIcon("sub.jpg");
		ImageIcon destroyerIcon = createImageIcon("destroyer.jpg");

		ImageIcon carrierIconFront = createImageIcon("carrier1.jpg");
		ImageIcon carrierIconMiddle = createImageIcon("carrier2.jpg");
		ImageIcon carrierIconEnd = createImageIcon("carrier4.jpg");
		ImageIcon waterIcon = createImageIcon("Water.JPG");
		ImageIcon hitIcon = createImageIcon("explosion.png");
		ImageIcon missIcon = createImageIcon("miss.png");

		JButton carrierLabel = new JButton(carrierIcon);
		JButton battleShipButton = new JButton(bshipIcon);
		JButton cruiserButton = new JButton(cruiserIcon);
		JButton subButton = new JButton(subIcon);
		JButton destroyerButton = new JButton(destroyerIcon);


	//Attach the drag source
    	//JLabelDragSource cruiserdragSource = new JLabelDragSource(label);
	
	/*
	AudioStream sounds[] = new AudioStream[10];
	AudioPlayer p = AudioPlayer.player;

 	void createSounds()
	{
		try{			
			sounds[0] = new AudioStream(new FileInputStream("sounds/laser.wav")); 		
			sounds[1] = new AudioStream(new FileInputStream("sounds/correct.wav")); 		
			sounds[2] = new AudioStream(new FileInputStream("sounds/incorrect.wav")); 		
			sounds[3] = new AudioStream(new FileInputStream("sounds/win.wav"));
			sounds[4] = new AudioStream(new FileInputStream("sounds/lose.wav"));
			sounds[5] = new AudioStream(new FileInputStream("sounds/cheat.wav")); 						
		}
		catch(IOException IOE){}
	}
	 */


	void showOpponentBoard()
	{
		for(int i=0;i<10;i++) for(int j=0;j<10;j++) buttonArray2[i][j].setVisible(true);	
		button1.setVisible(false);
		carrierLabel.setVisible(false);
		battleShipButton.setVisible(false);	
		cruiserButton.setVisible(false);
		subButton.setVisible(false);
		destroyerButton.setVisible(false);
		
	}


	void hideOpponentBoard()
	{
		for(int i=0;i<10;i++) for(int j=0;j<10;j++) buttonArray2[i][j].setVisible(false);	
		button1.setVisible(true);
	}

	void placeShip(int x, int y, String dir, String ship)
	{
		int i=0; //number of middle pieces
		int direction = 0;
		buttonArray[x][y].setIcon(carrierIconFront);
		if(ship.equals("carrier")){ i = 3; }
		if(ship.equals("battle")) {i = 2; }
		if(ship.equals("cruiser")){ i = 1; }
		if(ship.equals("sub")){ i = 1; }
		if(ship.equals("destroyer")){ i = 0; }
		
		if(dir.equalsIgnoreCase("north")){
			if( i+2 > y){
				y = 0;
				buttonArray[x][y].setIcon(carrierIconFront);
				for(int j=0; j<i; j++){	buttonArray[x][++y].setIcon(carrierIconMiddle);}
				buttonArray[x][++y].setIcon(carrierIconEnd);
			}
			else{
				buttonArray[x][y].setIcon(carrierIconFront);
				for(int j=0; j<i; j++){	buttonArray[x][--y].setIcon(carrierIconMiddle);}
				buttonArray[x][--y].setIcon(carrierIconEnd);
			}
		}
		if(dir.equalsIgnoreCase("east")){
			if( i+2 > (10-x)){
				x = 10-(i+2);
				buttonArray[x][y].setIcon(carrierIconFront);
				for(int j=0; j<i; j++){buttonArray[++x][y].setIcon(carrierIconMiddle);}
				buttonArray[++x][y].setIcon(carrierIconEnd);
			}
			else{
				buttonArray[x][y].setIcon(carrierIconFront);
				for(int j=0; j<i; j++){buttonArray[++x][y].setIcon(carrierIconMiddle);}
				buttonArray[++x][y].setIcon(carrierIconEnd);
			}
		}	
		if(dir.equalsIgnoreCase("south")){
			if( i+2 > 10-y){
				y = 10 -(i+2);
				buttonArray[x][y].setIcon(carrierIconFront);
				for(int j=0; j<i; j++){buttonArray[x][++y].setIcon(carrierIconMiddle);}
				buttonArray[x][++y].setIcon(carrierIconEnd);
			}
			else{
				buttonArray[x][y].setIcon(carrierIconFront);
				for(int j=0; j<i; j++){buttonArray[x][++y].setIcon(carrierIconMiddle);}
				buttonArray[x][++y].setIcon(carrierIconEnd);
			}
		}
		if(dir.equalsIgnoreCase("west")){
			if( i+2 > x){
				x = 0;
				buttonArray[x][y].setIcon(carrierIconFront);
				for(int j=0; j<i; j++){buttonArray[++x][y].setIcon(carrierIconMiddle);}
				buttonArray[++x][y].setIcon(carrierIconEnd);
			}
			else{
				buttonArray[x][y].setIcon(carrierIconFront);
				for(int j=0; j<i; j++){buttonArray[--x][y].setIcon(carrierIconMiddle);}
				buttonArray[--x][y].setIcon(carrierIconEnd);
			}
		}
	}


	public void actionPerformed(ActionEvent evt)
	{
		Object source = evt.getSource();
		int x = 0;
		int y = 0;
		String dir = "north"; 		
		if(source == button1){
		    showOpponentBoard();
		    client.sendData( "READY" );
		}
	
		if((source == carrierLabel)  || (source == battleShipButton) || (source == cruiserButton) || (source == subButton) || (source == 				destroyerButton)){
			String coords = JOptionPane.showInputDialog(null, "Enter coords of ship");
			String[] tokens;
           		tokens = coords.split(delims);
        		x = Integer.parseInt(tokens[0]);
			y = Integer.parseInt(tokens[1]);
			dir = JOptionPane.showInputDialog(null, "Enter direction of ship");
		}	
		
		if(source == carrierLabel){
			carrierLabel.setVisible(false);
			placeShip(x, y, dir, "carrier");
			client.sendData( "PLACE " + "CARRIER " + " " + x + " " + y + " " + dir);
		}

		if(source == battleShipButton){		
			battleShipButton.setVisible(false);
			placeShip(x, y, dir, "battle");
			client.sendData( "PLACE " + "BSHIP " + " " + x + " " + y + " " + dir);
			
		}

		if(source == cruiserButton){		
			cruiserButton.setVisible(false);
			placeShip(x, y, dir, "cruiser");
			client.sendData( "PLACE " + "CRUISER " + " " + x + " " + y + " " + dir);
		}

		if(source == subButton){
			subButton.setVisible(false);		
			placeShip(x, y, dir, "sub");
			client.sendData( "PLACE " + "SUB " + " " + x + " " + y + " " + dir);
		}

		if(source == destroyerButton){
			destroyerButton.setVisible(false);
			placeShip(x, y, dir, "destroyer");
			client.sendData( "PLACE " + "DESTROYER " + " " + x + " " + y + " " + dir);
		}		        

		for(int i=0;i<10;i++) for(int j=0;j<10;j++) if(source == buttonArray2[i][j])
		{
			client.sendData( "SHOOT " + " " + Integer.toString(i) + " " + Integer.toString(j) );
		}
	}

		/** Returns an ImageIcon, or null if the path was invalid. */
	    protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = BattleGUI.class.getResource(path);
		if (imgURL != null) {
		    return new ImageIcon(imgURL);
		} else {
		    System.err.println("Couldn't find file: " + path);
		    return null;
		}
	    }

	public void setClient(Client c)
	{
		client = c;
	}

	public void opponentHitShip(int x, int y)
	{
		buttonArray2[x][y].setIcon(hitIcon);
	}

	public void opponentMissShip(int x, int y)
	{
		buttonArray2[x][y].setIcon(missIcon);
	}

	public void yourShotMissed(int x, int y)
	{
		buttonArray[x][y].setIcon(hitIcon);
	}

	public void yourShotHit(int x, int y)
	{
		buttonArray[x][y].setIcon(missIcon);
	}


	public JPanel createContentPane()
	{
	
		//bottom panel
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.white);


		JPanel textPanel = new JPanel(new BorderLayout());
		textPanel.setBackground(Color.yellow);
		textPanel.setSize(400, 150);
		textPanel.setLocation(50,600);	
		

		enterField = new JTextField(); // create enterField
	      	enterField.setEditable( true );
	     	enterField.addActionListener(
		 new ActionListener() 
		 {
		    // send message to server
		    public void actionPerformed( ActionEvent event )
		    {
		       client.sendData( "CHAT " +event.getActionCommand() );
		       enterField.setText( "" );
		    } // end method actionPerformed
		 } // end anonymous inner class
	      	); // end call to addActionListener

	     	textPanel.add( enterField, BorderLayout.NORTH );

	      	displayArea = new JTextArea(); // create displayArea
	      	textPanel.add( new JScrollPane( displayArea ), BorderLayout.CENTER );
		
		panel.add(textPanel);
	

		//ship buttons
		carrierLabel.setSize(125, 25);
		carrierLabel.setLocation(188, 20);
		carrierLabel.setVisible(true);
		carrierLabel.addActionListener(this);
		panel.add(carrierLabel);
		
		battleShipButton.setSize(100, 25);
		battleShipButton.setLocation(200, 70);
		battleShipButton.setVisible(true);
		battleShipButton.addActionListener(this);
		panel.add(battleShipButton);

		cruiserButton.setSize(75, 25);
		cruiserButton.setLocation(213, 120);
		cruiserButton.setVisible(true);
		cruiserButton.addActionListener(this);
		panel.add(cruiserButton);

		subButton.setSize(75, 25);
		subButton.setLocation(213, 170);
		subButton.setVisible(true);
		subButton.addActionListener(this);
		panel.add(subButton);

		destroyerButton.setSize(50, 25);
		destroyerButton.setLocation(225, 220);
		destroyerButton.setVisible(true);
		destroyerButton.addActionListener(this);
		panel.add(destroyerButton);


        	// Creation of a panel to contain all the JButtons.
       		JPanel buttonPanel = new JPanel();     
       		buttonPanel.setLayout(null);
       		buttonPanel.setLocation(000, 000);
      		buttonPanel.setSize(500,600);
       		panel.add(buttonPanel);	
 

		buttonPanel.setBackground(Color.white);
		buttonPanel.setForeground(Color.black);
		
		Border border = new LineBorder(Color.BLACK, 1);
		
		final JPopupMenu menu = new JPopupMenu();
    
		// Create and add a menu item
		JMenuItem dropBomb = new JMenuItem("Drop Bomb Here");
		//dropBomb.addActionListener(new MenuActionListener());
		menu.add(dropBomb);

		/* Login shit we might use later
		usernameField.addActionListener(this);
		passwordField.addActionListener(this);
		usernameField.setLocation(100,100);
		passwordField.setLocation(100,00);
		usernameField.setVisible(true);
		passwordField.setVisible(true);
		usernameField.setEditable( true );
		passwordField.setEditable( true );*/
    
        	button1.setLocation(200,275);
        	button1.setSize(100,25);
		button1.setVisible(true);
		button1.setForeground(Color.black);
		button1.setBackground(Color.white);
		button1.setBorder(border);
		button1.addActionListener(this);
		buttonPanel.add(button1);
		
		button2.setLocation(10,110);
       		button2.setSize(100,25);
		button2.setVisible(false);
		button2.setForeground(Color.black);
		button2.setBackground(Color.white);
		button2.setBorder(border);
		button2.addActionListener(this);
		buttonPanel.add(button2);
		
		button3.setLocation(10,210);
        	button3.setSize(100,25);
		button3.setVisible(false);
		button3.setForeground(Color.black);
		button3.setBackground(Color.white);
		button3.setBorder(border);
		button3.addActionListener(this);
		buttonPanel.add(button3);
		
		button4.setLocation(10,310);
        	button4.setSize(100,25);
		button4.setVisible(false);
		button4.setForeground(Color.black);
		button4.setBackground(Color.white);
		button4.setBorder(border);
		button4.addActionListener(this);
		buttonPanel.add(button4);


		for(int i=0;i<10;i++) for(int j=0;j<10;j++)
		{		
			buttonArray[i][j] = new JButton(waterIcon);
			buttonArray[i][j].setLocation(125+24*i,325+24*j);
			buttonArray[i][j].setSize(25,25);
			buttonArray[i][j].setVisible(true);
			buttonArray[i][j].setForeground(Color.white);
			buttonArray[i][j].setBackground(Color.white);
			buttonArray[i][j].setBorder(border);
			buttonArray[i][j].addActionListener(this);
			buttonPanel.add(buttonArray[i][j]);
		}

		for(int i=0;i<10;i++) for(int j=0;j<10;j++)
		{			
			buttonArray2[i][j] = new JButton(waterIcon);
			buttonArray2[i][j].setLocation(125+24*i,50+24*j);
			buttonArray2[i][j].setSize(25,25);
			buttonArray2[i][j].setVisible(false);
			buttonArray2[i][j].setBackground(Color.white);
			buttonArray2[i][j].setBackground(Color.white);
			buttonArray2[i][j].setBorder(border);
			buttonArray2[i][j].addActionListener(this);

		    // Set the component to show the popup menu
		    buttonArray2[i][j].addMouseListener(new MouseAdapter() {
		        public void mousePressed(MouseEvent evt) {
		            if (evt.isPopupTrigger()) {
		                menu.show(evt.getComponent(), evt.getX(), evt.getY());
		            }
		        }
		        public void mouseReleased(MouseEvent evt) {
		            if (evt.isPopupTrigger()) {
		                menu.show(evt.getComponent(), evt.getX(), evt.getY());
		            }
		        }
		    });
				buttonPanel.add(buttonArray2[i][j]);
		}


		
		return panel;

		}
}



