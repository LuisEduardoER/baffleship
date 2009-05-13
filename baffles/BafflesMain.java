

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import  java.io.*;


class GUI extends JFrame implements ActionListener
{

	final int wrongGuessReward = -3;
	final int shootLaserReward = -1;
	final int correctGuessReward = 4;


	BlackBox masterBox;
	boolean known[][];
	int bafflesKnown;
	boolean disableGuessing;	
	int score;



	JFrame application = new JFrame();

        JButton buttonEasy = new JButton("Easy");
        JButton buttonMedium = new JButton("Medium");
        JButton buttonHard = new JButton("Hard");
        JLabel scoreLabel = new JLabel("Score: 10"); 

	JButton buttonCheatOn = new JButton("Cheat");
	JButton buttonCheatOff = new JButton("Stop cheating");

	JButton buttonStartOver = new JButton("Restart");

	JButton buttonArray[][] = new JButton[10][10];

	JButton buttonList[] = new JButton[40];
	
		//==========================================ICONS===============
		ImageIcon unguessedButtonIcon = createImageIcon("images/unguessed.gif");
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
	

 

					

	void DisplayUnknownBaffles()
	{
		for(int i=0;i<10;i++) for(int j=0;j<10;j++)
		{		
			Baffle bb = masterBox.getBaffleAtPosition(i,j);
			if ( bb == Baffle.LEFT ) buttonArray[i][j].setIcon(leftButtonIcon);
			if ( bb == Baffle.RIGHT ) buttonArray[i][j].setIcon(rightButtonIcon);
		}
	}


	void HideUnknownBaffles()
	{
		for(int i=0;i<10;i++)  for(int j=0;j<10;j++)
			if (!known[i][j]) buttonArray[i][j].setIcon(unguessedButtonIcon);
	}



	void BeginGame(int x, int y, int d)
	{
		HideDifficultyButtons();

		masterBox = new BlackBox(x,y,d);
		known = new boolean[x][y];
		bafflesKnown=0;
		disableGuessing=false;	
		score=10;
		UpdateScore(0);

		ShowBoard();
		buttonCheatOn.setVisible(true);
		buttonStartOver.setVisible(true);
	}

	
	void PlayerWins()
	{
		p.start(sounds[3]); createSounds();
		scoreLabel.setForeground(Color.red);
		scoreLabel.setText("YOU WIN! "+score);
		GameOver();
	}


	void PlayerLoses()
	{
		p.start(sounds[4]); createSounds();
		scoreLabel.setForeground(Color.red);
		scoreLabel.setText("YOU FAIL"); 
		GameOver();
	}

	
	void GameOver()
	{
		disableGuessing=true;
		buttonCheatOn.setVisible(false);
		DisplayUnknownBaffles();
		buttonStartOver.setText("Play Again?");
	}
	

	void UpdateScore(int s)
	{
		score+=s;
		scoreLabel.setText("Score: " +score);
		if (score<1) PlayerLoses();
	}



	void ShowBoard()
	{
		for(int i=0;i<10;i++) for(int j=0;j<10;j++) buttonArray[i][j].setVisible(true);
		for(int i=0;i<40;i++) buttonList[i].setVisible(true);
		scoreLabel.setVisible(true);		
	}


	void HideBoard()
	{
		for(int i=0;i<10;i++) for(int j=0;j<10;j++) buttonArray[i][j].setVisible(false);
		for(int i=0;i<40;i++) buttonList[i].setVisible(false);
		scoreLabel.setVisible(false);
		
	}




	void HideDifficultyButtons()
	{
		buttonEasy.setVisible(false);
		buttonMedium.setVisible(false);
		buttonHard.setVisible(false);
	}


	void ShowDifficultyButtons()
	{
		buttonEasy.setVisible(true);
		buttonMedium.setVisible(true);
		buttonHard.setVisible(true);
	}


	
	public void actionPerformed(ActionEvent evt)
	{
		Object source = evt.getSource();

		if(source == buttonEasy) { BeginGame(10,10,4); return; }

		if(source == buttonMedium) { BeginGame(10,10,7); return; }

		if(source == buttonHard) { BeginGame(10,10,10); return; }

		if(source == buttonCheatOn)
		{
			p.start(sounds[5]); createSounds();
			disableGuessing=true;
			buttonCheatOn.setVisible(false);
			DisplayUnknownBaffles();
			buttonCheatOff.setVisible(true);
			return;
		}


		if(source == buttonCheatOff)
		{
			disableGuessing=false;
			buttonCheatOff.setVisible(false);
			HideUnknownBaffles();
			buttonCheatOn.setVisible(true);
			return;
		}

		if(source == buttonStartOver)
		{
			buttonStartOver.setVisible(false);
			buttonCheatOff.setVisible(false);
			buttonCheatOn.setVisible(false);
			scoreLabel.setVisible(false);
			HideBoard();

			for(int i=0;i<10;i++) for(int j=0;j<10;j++) buttonArray[i][j].setIcon(unguessedButtonIcon);
			
			for(int i=0;i<40;i++) buttonList[i].setBackground(Color.white);

			ShowDifficultyButtons();

			return;
		}


		for(int i=0;i<40;i++) if(source == buttonList[i])
		{

			p.start(sounds[0]); createSounds();			

			//if the button is already white then that means it has not been used before
			//so we charge a penalty for the shot
			//but if the game is over or we are in cheat mode, we can shoot for free
			if ( (!disableGuessing) && (buttonList[i].getBackground() == Color.white) )
					UpdateScore(shootLaserReward);


			//random color code from http://leepoint.net/notes-java/algorithms/random/random-api.html
			// 1/(2^24) chance this picks white again, but thats too unlikely to care about
			Random r = new Random(); Color c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
			
	
			//change color of the clicked button and of the one where  the laser exits
			buttonList[i].setBackground(c);
			buttonList[masterBox.shootLaser(i)].setBackground(c);
		} 



		//you cant make guesses while cheating so just stop trying now
		if (disableGuessing) return;

		for(int i=0;i<10;i++) for(int j=0;j<10;j++) if(source == buttonArray[i][j])
		{
			if (known[i][j]) return; //clicking on a known square is pointless (haha)
			known[i][j]=true;

			Baffle bb = masterBox.getBaffleAtPosition(i,j);
			if ( bb == Baffle.NONE )
			{
				buttonArray[i][j].setIcon(guessedButtonIcon);
				UpdateScore(wrongGuessReward);
				if (score>0) { p.start(sounds[2]); createSounds(); }
			}
			else 
			{
				p.start(sounds[1]); createSounds();

				if ( bb == Baffle.LEFT ) buttonArray[i][j].setIcon(leftButtonIcon);
				else buttonArray[i][j].setIcon(rightButtonIcon);

				bafflesKnown++;
				UpdateScore(correctGuessReward);

				if (bafflesKnown == masterBox.getNumBaffles() ) PlayerWins();
				
			}
			return;
		}

	}



	/** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = BafflesMain.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }







	public JPanel createContentPane()
	{

		createSounds();

		buttonEasy.addActionListener(this);
		buttonMedium.addActionListener(this);
		buttonHard.addActionListener(this);
		buttonCheatOn.addActionListener(this);
		buttonCheatOff.addActionListener(this);
		buttonStartOver.addActionListener(this);

		
		//bottom panel
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.black);
		
		// Creation of a Panel to contain the title label
        	JPanel titlePanel = new JPanel();
	        titlePanel.setLayout(null);
	        titlePanel.setLocation(0, 0);
        	titlePanel.setSize(600, 100);
        	titlePanel.setBackground(Color.lightGray);
        	panel.add(titlePanel);

		
		JLabel titleLabel = new JLabel("Baffles!");
		Font font = new Font("Courier New", Font.PLAIN, 72);
        	titleLabel.setLocation(0, 10);
        	titleLabel.setSize(600, 60);
        	titleLabel.setHorizontalAlignment(0);		
		titleLabel.setFont(font);
        	titleLabel.setForeground(Color.black);
        	titleLabel.setBackground(Color.lightGray);
        	titlePanel.add(titleLabel);
        
        	JLabel nameLabel = new JLabel("By Frank Rowe & John Gaetano");
			Font font2 = new Font("Courier New", Font.PLAIN, 12);
        	nameLabel.setLocation(200, 70);
        	nameLabel.setSize(200, 30);
        	nameLabel.setHorizontalAlignment(0);		
			nameLabel.setFont(font2);
        	nameLabel.setForeground(Color.black);
        	nameLabel.setBackground(Color.lightGray);
        	titlePanel.add(nameLabel);
        	


        
        	// Creation of a panel to contain all the JButtons.
       		JPanel buttonPanel = new JPanel();     
       		buttonPanel.setLayout(null);
       		buttonPanel.setLocation(0, 100);
      		buttonPanel.setSize(600, 700);
       		panel.add(buttonPanel);	
 
       		 			
        	scoreLabel.setLocation(20, 625);
        	scoreLabel.setSize(400, 25);
        	scoreLabel.setHorizontalAlignment(JLabel.LEFT);		
			scoreLabel.setFont(font2);
			Font font3 = new Font("Arial", Font.PLAIN, 36);
			scoreLabel.setFont(font3);
        	scoreLabel.setForeground(Color.blue);
        	scoreLabel.setBackground(Color.lightGray);
        	scoreLabel.setVisible(false);
        	buttonPanel.add(scoreLabel);

		buttonPanel.setBackground(Color.lightGray);
		buttonPanel.setForeground(Color.gray);
		
		buttonEasy.setLocation(20,20);
		buttonEasy.setSize(150,25);
		buttonEasy.setBackground(Color.white);
		buttonPanel.add(buttonEasy);
		
		buttonMedium.setLocation(220,20);
		buttonMedium.setSize(150,25);
		buttonMedium.setBackground(Color.white);
		buttonPanel.add(buttonMedium);

		buttonHard.setLocation(420,20);
		buttonHard.setSize(150,25);
		buttonHard.setBackground(Color.white);
		buttonPanel.add(buttonHard);
		
	
		buttonCheatOn.setLocation(420,620);
		buttonCheatOn.setSize(150,25);
		buttonCheatOn.setVisible(false);
		buttonPanel.add(buttonCheatOn);

		buttonCheatOff.setLocation(420,620);
		buttonCheatOff.setSize(150,25);
		buttonCheatOff.setVisible(false);
		buttonPanel.add(buttonCheatOff);
		

		buttonStartOver.setLocation(250,620);
		buttonStartOver.setSize(150,25);
		buttonStartOver.setVisible(false);
		buttonPanel.add(buttonStartOver);


		for(int i=0;i<10;i++) for(int j=0;j<10;j++)
		{
			
			buttonArray[i][j] = new JButton("", unguessedButtonIcon);
			buttonArray[i][j].setLocation(50+50*i,60+50*j);
			buttonArray[i][j].setSize(50,50);
			buttonArray[i][j].setVisible(false);
			buttonArray[i][j].addActionListener(this);
			buttonPanel.add(buttonArray[i][j]);
		}

		
		for(int i=0;i<40;i++)
		{
			buttonList[i] = new JButton(Integer.toString(i));
			buttonList[i].setBackground(Color.white);
			buttonList[i].setForeground(Color.black);
			buttonList[i].setSize(50,50);
			buttonList[i].setVisible(false);
			buttonList[i].addActionListener(this);
				if (i<10) buttonList[i].setLocation(0,510-50*i);
				else if (i<20) buttonList[i].setLocation(50*i-450,10);
				else if (i<30) buttonList[i].setLocation(550,50*i-940);
				else buttonList[i].setLocation(2000-50*i,560);
			buttonPanel.add(buttonList[i]);
		}





		return panel;

	}



}





public class BafflesMain extends JFrame
{

	public static void createAndShowGUI()
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();  
		Dimension screenSize = toolkit.getScreenSize();


		JFrame frame = new JFrame("Baffles!");		
		GUI menu = new GUI();
		frame.setContentPane(menu.createContentPane());
        	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(600,800);

		//place frame in middle of screen
		int x = (screenSize.width - frame.getWidth()) / 2;  
		int y = (screenSize.height - frame.getHeight()) / 2;  
 		frame.setLocation(x, y); 

 		frame.setVisible(true);

	}
	
		
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            		public void run() { createAndShowGUI(); }
        	}) ;
	}



}
