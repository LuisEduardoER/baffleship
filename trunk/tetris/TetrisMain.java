//Frank Rowe
//TetrisMain.java

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

import  java.io.*;



class TetrisMain extends JFrame implements KeyListener, ActionListener
{
	Toolkit toolkit = Toolkit.getDefaultToolkit();  
	Dimension screenSize = toolkit.getScreenSize();
	
	int wait = 1000;
	int rowsCleared = 0;
	
	TetrisMusic tetrisMusic = new TetrisMusic();

	ScorePanel scorePanel = new ScorePanel(this, tetrisMusic);
	Timer downTimer;
	GameBoard gameBoard = new GameBoard(scorePanel, this);
	//String levelChoice;

	
	//Create a new thread for dropping the piece
		 	//Color transp = new Color(0f, 0f, 0f, 0.3f);


	Cheat konami = new Cheat(KeyEvent.VK_UP, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_DOWN,
				 KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT , KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);

	Cheat slow = new Cheat(KeyEvent.VK_S, KeyEvent.VK_L, KeyEvent.VK_O, KeyEvent.VK_W);
	Cheat setMeUp = new Cheat(KeyEvent.VK_C, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_H);
	Cheat dots = new Cheat(KeyEvent.VK_D, KeyEvent.VK_O, KeyEvent.VK_T, KeyEvent.VK_S);

	Cheat randomColorsCheat = new Cheat(KeyEvent.VK_D, KeyEvent.VK_I, KeyEvent.VK_S, KeyEvent.VK_C, KeyEvent.VK_O);
	boolean randomColorsEnabled = false;



	public TetrisMain()
	{


		super("tetris");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(212,726);
		//place frame in middle of screen
		int x = (screenSize.width - getWidth()) / 2;  
		int y = (screenSize.height - getHeight()) / 2;  

		//getLevel();
		//scorePanel.setInitialLevel(levelChoice);
		downTimer =   new Timer(wait / (1+ scorePanel.level /2), this);

		tetrisMusic.startMusic();
		
		add(scorePanel);
 		setLocation(x, y); 
		setFocusable(true);
		add(gameBoard);
		addKeyListener(this);
		setFocusable(true); 
		downTimer.start();

		setVisible(true);




	}

	
	public void actionPerformed(ActionEvent e)
	{
		if (randomColorsEnabled ) gameBoard.randomizeColors();
		if(gameBoard.playing){gameBoard.down();repaint();}

	}


	
	public void keyPressed(KeyEvent event){
		if (randomColorsEnabled ) gameBoard.randomizeColors();

		int k = event.getKeyCode();
		switch( k )
		{
			case (KeyEvent.VK_G ):	gameBoard.toggleGhostPiece();repaint();	return;	// toggle ghost piece
			case (KeyEvent.VK_M ):	 tetrisMusic.toggleMusic(); return;	// toggle music		
			case (KeyEvent.VK_P ):  gameBoard.playing= !gameBoard.playing;return;// pause/unpause

			case (KeyEvent.VK_EQUALS ): gameBoard.scorePanel.increaseLevel();  speedUp(scorePanel.level); repaint(); return;		
		}
		
		if ( konami.nextKey( k) ) gameBoard.addBigI();
		
		if ( slow.nextKey( k) )
		{
			scorePanel.extralevels -= scorePanel.level;
			scorePanel.setLevel(); 
			speedUp(scorePanel.level);
			repaint();
		}  //slow down

		if ( (scorePanel.score ==0 ) && ( setMeUp.nextKey(k) ) )   gameBoard.SetMeUpCheat();

		if ( dots.nextKey( k ) ) gameBoard.add4dots();		

		if ( randomColorsCheat.nextKey( k ) ) randomColorsEnabled = !randomColorsEnabled;
	
		if(!gameBoard.playing) return;

		switch( k )
		{
			case (KeyEvent.VK_UP ):		gameBoard.clock();	break;
			case (KeyEvent.VK_X ):      	gameBoard.clock();    	break;
			case (KeyEvent.VK_Z ):      	gameBoard.counter();  	break;
			case (KeyEvent.VK_LEFT ):   	gameBoard.left();	break;
			case (KeyEvent.VK_RIGHT ):   	gameBoard.right();	break;
			case (KeyEvent.VK_DOWN ):   	gameBoard.down();	break;
			case (KeyEvent.VK_SPACE ):   	gameBoard.hardDrop();	break;	

		}
		repaint();
	}
		
	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}
	
	public static void main(String[] args) {
		TetrisMain tetris = new TetrisMain();
	}


	//increase the speed of dropping the piece respective of the players level
	public void speedUp(int level){
		downTimer.stop();
		downTimer = new Timer(wait / (1+ scorePanel.level /2), this);
		downTimer.start();
	}



	//start a new game
	public void reset(){	
		downTimer.stop();
		//tetrisMusic.stopMusic();
		downTimer = new Timer(wait, this);
		downTimer.start();
		//tetrisMusic.startMusic();
		gameBoard.reset();
	}

			
}






























