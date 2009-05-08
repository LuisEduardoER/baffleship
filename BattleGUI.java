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

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

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

	JButton bottomArray[][] = new JButton[10][10];

	JButton topArray[][] = new JButton[10][10];

	JTextField enterField; // enters information from user
   	JTextArea displayArea; // display information to user

	Client client;

	String delims = "[ ]+";

	Object shipType = 0;
	Object location = 0;
	int xLoc = 0;
	int yLoc = 0;
	
	boolean carrierPlaced = false;
	boolean bshipPlaced = false;
	boolean cruiserPlaced = false;
	boolean subPlaced = false;
	boolean destroyerPlaced = false;
	
	Fleet fleet = new Fleet();
	
		//==========================================ICONS===============
		ImageIcon carrierIcon = createImageIcon("pictures/carrier.png");
		ImageIcon bshipIcon = createImageIcon("pictures/battleship.png");
		ImageIcon cruiserIcon = createImageIcon("pictures/cruiser.png");
		ImageIcon subIcon = createImageIcon("pictures/sub.png");
		ImageIcon destroyerIcon = createImageIcon("pictures/destroyer.png");

		ImageIcon carrierIconFront = createImageIcon("pictures/ship4-1-r.png");	
		ImageIcon carrierIconFrontNS = createImageIcon("pictures/ship4-1.png");
		ImageIcon carrierIconMiddle = createImageIcon("pictures/ship4-2-r.png");
		ImageIcon carrierIconMiddleNS = createImageIcon("pictures/ship4-2.png");
		ImageIcon carrierIconEnd = createImageIcon("pictures/ship4-4-r.png");
		ImageIcon carrierIconEndNS = createImageIcon("pictures/ship4-4.png");
		
		ImageIcon battleIconFront = createImageIcon("pictures/ship3-1-r.png");	
		ImageIcon battleIconFrontNS = createImageIcon("pictures/ship3-1.png");
		ImageIcon battleIconMiddle = createImageIcon("pictures/ship3-2-r.png");
		ImageIcon battleIconMiddleNS = createImageIcon("pictures/ship3-2.png");
		ImageIcon battleIconEnd = createImageIcon("pictures/ship3-3-r.png");
		ImageIcon battleIconEndNS = createImageIcon("pictures/ship3-3.png");
		
		ImageIcon subIconFront = createImageIcon("pictures/ship2-1-r.png");	
		ImageIcon subIconFrontNS = createImageIcon("pictures/ship2-1.png");
		ImageIcon subIconMiddle = createImageIcon("pictures/ship2-3-r.png");
		ImageIcon subIconMiddleNS = createImageIcon("pictures/ship2-3.png");
		ImageIcon subIconEnd = createImageIcon("pictures/ship2-2-r.png");
		ImageIcon subIconEndNS = createImageIcon("pictures/ship2-2.png");
		
		ImageIcon waterIcon = createImageIcon("pictures/sea.png");
		ImageIcon hitIcon = createImageIcon("pictures/hit.png");
		ImageIcon missIcon = createImageIcon("pictures/water.png");

		JButton carrierLabel = new JButton(carrierIcon);
		JButton battleShipButton = new JButton(bshipIcon);
		JButton cruiserButton = new JButton(cruiserIcon);
		JButton subButton = new JButton(subIcon);
		JButton destroyerButton = new JButton(destroyerIcon);
	
	
	AudioStream sounds[] = new AudioStream[10];
	AudioPlayer p = AudioPlayer.player;

 	void createSounds()
	{
		try{			
			sounds[0] = new AudioStream(new FileInputStream("spash.wav")); 		
			sounds[1] = new AudioStream(new FileInputStream("bomb-03.wav")); 
			sounds[2] = new AudioStream(new FileInputStream("youwin.wav"));//yeah
			sounds[3] = new AudioStream(new FileInputStream("youlose.wav"));							
		}
		catch(IOException IOE){}
	}
	 
   public void youWin(){
       try{ Thread.sleep(1500);} catch(Exception e){}
        p.start(sounds[2]);createSounds();
    }
    
    public void youLose(){
       try{ Thread.sleep(1500);}catch(Exception e){}
        p.start(sounds[3]);createSounds();
    }

	void showOpponentBoard()
	{
		for(int i=0;i<10;i++) for(int j=0;j<10;j++) topArray[i][j].setVisible(true);	
		button1.setVisible(false);
		carrierLabel.setVisible(false);
		battleShipButton.setVisible(false);	
		cruiserButton.setVisible(false);
		subButton.setVisible(false);
		destroyerButton.setVisible(false);
		
	}


	void hideOpponentBoard()
	{
		for(int i=0;i<10;i++) for(int j=0;j<10;j++) topArray[i][j].setVisible(false);	
		button1.setVisible(true);
	}

	void placeShip(int x, int y, String dir, String ship)
	{
		int i=0; //number of middle pieces
		int direction = 0;
		
		ImageIcon front= createImageIcon("carrier.jpg");
		ImageIcon middle= createImageIcon("carrier.jpg");
		ImageIcon end= createImageIcon("carrier.jpg");
		ImageIcon frontNS= createImageIcon("carrier.jpg");
		ImageIcon middleNS= createImageIcon("carrier.jpg");
		ImageIcon endNS= createImageIcon("carrier.jpg");
		
		bottomArray[x][y].setIcon(carrierIconFront);
		if(ship.equals("CARRIER")){
		     i = 3;
		     front = carrierIconFront;
		     middle = carrierIconMiddle;
		     end = carrierIconEnd;
		     frontNS = carrierIconFrontNS;
		     middleNS = carrierIconMiddleNS;
		     endNS = carrierIconEndNS;
	     }
		if(ship.equals("BSHIP")) {
		     i = 2;
		     front = battleIconFront;
		     middle = battleIconMiddle;
		     end = battleIconEnd;
		     frontNS = battleIconFrontNS;
		     middleNS = battleIconMiddleNS;
		     endNS = battleIconEndNS;
	     }
		if(ship.equals("CRUISER")){
		     i = 1;
		     front = battleIconFront;
		     middle = battleIconMiddle;
		     end = battleIconEnd;
		     frontNS = battleIconFrontNS;
		     middleNS = battleIconMiddleNS;
		     endNS = battleIconEndNS;
	     }
		if(ship.equals("SUB")){
		     i = 1;
		     front = subIconFront;
		     middle = subIconMiddle;
		     end = subIconEnd;
		     frontNS = subIconFrontNS;
		     middleNS = subIconMiddleNS;
		     endNS = subIconEndNS;
	     }
		if(ship.equals("DESTROYER")){
		     i = 0;
			 front = battleIconFront;
		     middle = battleIconMiddle;
		     end = battleIconEnd;
		     frontNS = battleIconFrontNS;
		     middleNS = battleIconMiddleNS;
		     endNS = battleIconEndNS;
	     }
		
		if(dir.equalsIgnoreCase("north")){
			if( i+2 > y){
				y = 0;
				client.sendData( "PLACE " + ship + " " + x + " " + y + " " + "south");
				bottomArray[x][y].setIcon(frontNS);
				for(int j=0; j<i; j++){	bottomArray[x][++y].setIcon(middleNS);}
				bottomArray[x][++y].setIcon(endNS);
			}
			else{
			    client.sendData( "PLACE " + ship + " " + x + " " + y + " " + dir);
				bottomArray[x][y].setIcon(endNS);
				for(int j=0; j<i; j++){	bottomArray[x][--y].setIcon(middleNS);}
				bottomArray[x][--y].setIcon(frontNS);
			}
		}
		if(dir.equalsIgnoreCase("east")){
			if( i+2 > (10-x)){
				x = 10-(i+2);
				client.sendData( "PLACE " + ship + " " + x + " " + y + " " + "west");
				bottomArray[x][y].setIcon(end);
				for(int j=0; j<i; j++){bottomArray[++x][y].setIcon(middle);}
				bottomArray[++x][y].setIcon(front);
			}
			else{
			    client.sendData( "PLACE " + ship + " " + x + " " + y + " " + dir);
				bottomArray[x][y].setIcon(end);
				for(int j=0; j<i; j++){bottomArray[++x][y].setIcon(middle);}
				bottomArray[++x][y].setIcon(front);
			}
		}	
		if(dir.equalsIgnoreCase("south")){
			if( i+2 > 10-y){
				y = 10 -(i+2);
				client.sendData( "PLACE " + ship + " " + x + " " + y + " " + "north");
				bottomArray[x][y].setIcon(frontNS);
				for(int j=0; j<i; j++){bottomArray[x][++y].setIcon(middleNS);}
				bottomArray[x][++y].setIcon(endNS);
			}
			else{
			    client.sendData( "PLACE " + ship + " " + x + " " + y + " " + dir);
				bottomArray[x][y].setIcon(frontNS);
				for(int j=0; j<i; j++){bottomArray[x][++y].setIcon(middleNS);}
				bottomArray[x][++y].setIcon(endNS);
			}
		}
		if(dir.equalsIgnoreCase("west")){
			if( i+2 > x){
				x = 0;
				client.sendData( "PLACE " + ship + " " + x + " " + y + " " + "east");
				bottomArray[x][y].setIcon(end);
				for(int j=0; j<i; j++){bottomArray[++x][y].setIcon(middle);}
				bottomArray[++x][y].setIcon(front);
			}
			else{
			    client.sendData( "PLACE " + ship + " " + x + " " + y + " " + dir);
				bottomArray[x][y].setIcon(front);
				for(int j=0; j<i; j++){bottomArray[--x][y].setIcon(middle);}
				bottomArray[--x][y].setIcon(end);
			}
		}
	}


	public void actionPerformed(ActionEvent evt)
	{
		Object source = evt.getSource();
		//Object shipType = evt.getSource();
		int x = 0;
		int y = 0;
		String dir = "north"; 		
		if(source == button1){
		    showOpponentBoard();
		    client.sendData( "READY" );
		}
	
		if((source == carrierLabel)  || (source == battleShipButton) || (source == cruiserButton) || (source == subButton) || (source == 				destroyerButton)){
			shipType = source;
		}	

		for(int i=0;i<10;i++) for(int j=0;j<10;j++) if(source == topArray[i][j])
		{
			client.sendData( "SHOOT " + " " + Integer.toString(i) + " " + Integer.toString(j) );
		}

		for(int i=0;i<10;i++) for(int j=0;j<10;j++) if(source == bottomArray[i][j])
		{
			if(location == (Integer)0) {
				location = source;
				xLoc = i;
				yLoc = j;
				//System.out.println(x+", "+y);
			}
			else{
				    location = 0;
				    if( (j < yLoc) && (i == xLoc)) dir = "north";
				    if( (j > yLoc) && (i == xLoc)) dir = "south";
				    if( (i > xLoc) && (j == yLoc)) dir = "east";
				    if( (i < xLoc) && (j == yLoc)) dir = "west";
				    System.out.println(dir);

				        if(shipType == carrierLabel){
				            if(carrierPlaced) return;
    				        else{
				                Ship carrier = new Ship(SquareType.CARRIER, new Point(xLoc, yLoc), Direction.parseDirection(dir));
				                if(fleet.addShip(carrier))
				                {
					                carrierLabel.setVisible(false);
					                carrierPlaced = true;
					                placeShip(xLoc, yLoc, dir, "CARRIER");
					            }
				            }
				        }
                  
                  
				        if(shipType == battleShipButton){		
				            if(bshipPlaced) return;
            				else{
				                Ship battleShip = new Ship(SquareType.BSHIP, new Point(xLoc, yLoc), Direction.parseDirection(dir));
				                if(fleet.addShip(battleShip))
				                {
					                battleShipButton.setVisible(false);
					                bshipPlaced = true;
					                placeShip(xLoc, yLoc, dir, "BSHIP");
					            }	
				            }
				        }
                    
				        if(shipType == cruiserButton){	
				            if(cruiserPlaced) return;
				            else{	
				                Ship cruiserShip = new Ship(SquareType.CRUISER, new Point(xLoc, yLoc), Direction.parseDirection(dir));
				                if(fleet.addShip(cruiserShip))
				                {
					                cruiserButton.setVisible(false);
					                cruiserPlaced = true;
					                placeShip(xLoc, yLoc, dir, "CRUISER");
					            }
				            }
				        }
                 
				        if(shipType == subButton){
				           if(subPlaced) return;
				           else{
				               Ship subShip = new Ship(SquareType.SUB, new Point(xLoc, yLoc), Direction.parseDirection(dir));
				               if(fleet.addShip(subShip))
				               {
					                subButton.setVisible(false);
					                subPlaced = true;		
					                placeShip(xLoc, yLoc, dir, "SUB");
					           }
				            }
				         }
                   
				        if(shipType == destroyerButton){
				            if(destroyerPlaced) return;
        				    else{
				                Ship destroyerShip = new Ship(SquareType.DESTROYER, new Point(xLoc, yLoc), Direction.parseDirection(dir));
				                if(fleet.addShip(destroyerShip))
				                {
					                destroyerButton.setVisible(false);
					                destroyerPlaced = true;
            				        placeShip(xLoc, yLoc, dir, "DESTROYER");
					            }
				            }
				        }		        
		            }
		         if(fleet.numShips() == 5) showOpponentBoard();
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
		bottomArray[x][y].setIcon(hitIcon);
		p.start(sounds[1]);createSounds();
	}

	public void opponentMissShip(int x, int y)
	{
		bottomArray[x][y].setIcon(missIcon);
		p.start(sounds[0]);createSounds();
	}

	public void yourShotMissed(int x, int y)
	{
		topArray[x][y].setIcon(missIcon);
		p.start(sounds[0]);createSounds();
	}

	public void yourShotHit(int x, int y)
	{
		topArray[x][y].setIcon(hitIcon);
		p.start(sounds[1]);createSounds();
	}


	public JPanel createContentPane()
	{
	    createSounds();
		//bottom panel
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.white);
        Border border1 = new LineBorder(Color.BLACK, 3);

		JPanel textPanel = new JPanel(new BorderLayout());
		textPanel.setBackground(Color.yellow);
		textPanel.setSize(400, 600);
		textPanel.setLocation(350,50);	
		textPanel.setBorder(border1);
		

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
	
	    Border shipBorder = new LineBorder(Color.white, 1);

		//ship buttons
		carrierLabel.setSize(160, 25);
		carrierLabel.setLocation(20, 70);
		carrierLabel.setVisible(true);
		carrierLabel.setBorder(shipBorder);
		carrierLabel.addActionListener(this);
		panel.add(carrierLabel);
		
		battleShipButton.setSize(128, 25);
		battleShipButton.setLocation(20, 120);
		battleShipButton.setVisible(true);
		battleShipButton.setBorder(shipBorder);
		battleShipButton.addActionListener(this);
		panel.add(battleShipButton);

		cruiserButton.setSize(96, 25);
		cruiserButton.setLocation(20, 170);
		cruiserButton.setVisible(true);
		cruiserButton.setBorder(shipBorder);
		cruiserButton.addActionListener(this);
		panel.add(cruiserButton);

		subButton.setSize(96, 25);
		subButton.setLocation(20, 220);
		subButton.setVisible(true);
		subButton.setBorder(shipBorder);
		subButton.addActionListener(this);
		panel.add(subButton);

		destroyerButton.setSize(64, 25);
		destroyerButton.setLocation(20, 270);
		destroyerButton.setVisible(true);
		destroyerButton.setBorder(shipBorder);
		destroyerButton.addActionListener(this);
		panel.add(destroyerButton);


        	// Creation of a panel to contain all the JButtons.
       		JPanel buttonPanel = new JPanel();     
       		buttonPanel.setLayout(null);
       		buttonPanel.setLocation(000, 000);
      		buttonPanel.setSize(500,700);
       		panel.add(buttonPanel);	
 

		buttonPanel.setBackground(Color.white);
		buttonPanel.setForeground(Color.black);
		
		Border border = new LineBorder(Color.BLACK, 1);

		for(int i=0;i<10;i++) for(int j=0;j<10;j++)
		{		
			bottomArray[i][j] = new JButton(waterIcon);
			bottomArray[i][j].setLocation(20+31*i,340+31*j);
			bottomArray[i][j].setSize(32,32);
			bottomArray[i][j].setVisible(true);
			bottomArray[i][j].setForeground(Color.white);
			bottomArray[i][j].setBackground(Color.white);
			bottomArray[i][j].setBorder(border);
			bottomArray[i][j].addActionListener(this);
			buttonPanel.add(bottomArray[i][j]);
		}

		for(int i=0;i<10;i++) for(int j=0;j<10;j++)
		{			
			topArray[i][j] = new JButton(waterIcon);
			topArray[i][j].setLocation(20+31*i,10+31*j);
			topArray[i][j].setSize(32,32);
			topArray[i][j].setVisible(false);
			topArray[i][j].setBackground(Color.white);
			topArray[i][j].setBackground(Color.white);
			topArray[i][j].setBorder(border);
			topArray[i][j].addActionListener(this);
			buttonPanel.add(topArray[i][j]);
		}


		
		return panel;

		}
}



