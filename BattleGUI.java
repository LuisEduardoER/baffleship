

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import javax.swing.border.*;

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

	
	
		//==========================================ICONS===============
	/*	ImageIcon unguessedButtonIcon = createImageIcon("images/unguessed.gif");
		ImageIcon leftButtonIcon = createImageIcon("images/left.gif");
		ImageIcon rightButtonIcon = createImageIcon("images/right.gif");
		ImageIcon guessedButtonIcon = createImageIcon("images/guessed.gif");

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
	}


	void hideOpponentBoard()
	{
		for(int i=0;i<10;i++) for(int j=0;j<10;j++) buttonArray2[i][j].setVisible(false);	
		button1.setVisible(true);
	}

	
	public void actionPerformed(ActionEvent evt)
	{
		Object source = evt.getSource();

 		String username = usernameField.getText();
		String password = passwordField.getText();
		
		if(source == button1){
		    showOpponentBoard();
		    client.sendData( "READY" );
		}
		        

		for(int i=0;i<10;i++) for(int j=0;j<10;j++) if(source == buttonArray[i][j])
		{
			client.sendData( "SHOOT" + " " + Integer.toString(i) + " " + Integer.toString(j) );
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
		
		/* Creation of a Panel to contain the title label
        	JPanel titlePanel = new JPanel();
	        titlePanel.setLayout(null);
	        titlePanel.setLocation(0, 0);
        	titlePanel.setSize(800, 100);
        	titlePanel.setBackground(Color.white);
        	panel.add(titlePanel);

		
		JLabel titleLabel = new JLabel("Baffleship!");
		Font font = new Font("Lucida Sans", Font.PLAIN, 14);
        	titleLabel.setLocation(0, 0);
        	titleLabel.setSize(300, 100);
        	titleLabel.setHorizontalAlignment(0);		
		titleLabel.setFont(font);
        	titleLabel.setForeground(Color.black);
        	titleLabel.setBackground(Color.black);
        	titlePanel.add(titleLabel);*/

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
		dropBomb.addActionListener(this);
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
			
			buttonArray[i][j] = new JButton("");
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
			
			buttonArray2[i][j] = new JButton("");
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

