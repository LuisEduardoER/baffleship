//Frank Rowe
//ScorePanel.java

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import java.io.*;



class ScorePanel extends JPanel
{		
	Font font = new Font("Arial", Font.PLAIN, 12);

	//intial scoring values
	String name = "___";
	int score = -1;
	int highScore=0;
	int level = 0;
	int extralevels = 0;
	int totalRows = 0;

	TetrisMain tetris;
	TetrisMusic sound;
	
	ScorePanel(TetrisMain t, TetrisMusic m){
		tetris = t;	
		sound = m;
	}

	//increment score
	public void increaseScore(){
		score++;
	}

	//increase level for free
	public void increaseLevel(){
		extralevels++;
		setLevel();
	}




	//calculate number of points earned based off of the number of rows cleared
	public void rowScore(int numRows){
		
		sound.rowSound();  //play a sound   

		totalRows += numRows;

		switch(numRows)
		{
			case 1: { score = score + 40*(level+1); break;}
			case 2: { score = score + 100*(level+1); break;}
			case 3: { score = score + 300*(level+1); break;}
			case 4: { score = score + 1200*(level+1); break;}
			case 5: { score = score + 6000*(level+1); break;}   //possible with the bigI piece
			case 6: { score = score + 30000*(level+1); break;}
			case 7: { score = score + 100000*(level+1); break;}
			default: score++;
		}

		//I think the level should be updated AFTER getting the score increased based on current level
		setLevel();
	}


	//level is based on the number of rows cleared
	public void setLevel(){
		level = extralevels+ totalRows/10;
	}

	//after losing, if the player has a high score, record their name
	//then reset everything to play again
	public void setHighScore(){
		if(score>highScore){
			highScore = score;
			sound.winSound();	
			name = JOptionPane.showInputDialog(null,
	  			"What is your name?",
	  			"High Score!",
	  			JOptionPane.QUESTION_MESSAGE);
		}
		else{sound.loseSound(); JOptionPane.showMessageDialog(null, "You Lose!");}
		totalRows = 0;
		level = 0;
		score = -1;
		extralevels=0;
		tetris.reset();
	}

}
			
	
