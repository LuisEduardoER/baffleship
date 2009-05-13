//Frank Rowe
//John Gaetano
//GameBoard.java

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;


public class GameBoard extends JPanel

{
	final int PLAYABLE_WIDTH = 10; //board dimension
	final int FULL_WIDTH = PLAYABLE_WIDTH;

	final int PLAYABLE_HEIGHT = 21;
	final int EXTRA_HEIGHT = 12; //some extra area up top to place or display new pieces
	final int FULL_HEIGHT = PLAYABLE_HEIGHT + EXTRA_HEIGHT;

	final int TOO_HIGH = 0; //if any part of a piece is here or above, it is above the normal play area

	//where each new piece is placed before dropping down into the play area
	final Point PIECE_START = new Point( 1, - EXTRA_HEIGHT ); 	//0,0 is upper left 

	//array of Blocks that is our game board	
	public Block boardArray[][] = new Block[ PLAYABLE_WIDTH ][ PLAYABLE_HEIGHT ]; 

	private Piece playerPiece;  //this is the active piece

	//these are the upcoming pieces(s)
	public java.util.List<Piece> waitingPieces = new ArrayList<Piece>();
	final int minimumWaitingPieces = 3;

	public boolean playing;
	
	public ScorePanel scorePanel;
	//DropThread dropThread;
	TetrisMain tetris;
	boolean ghostPiece=false;

	
	//Constructor
	//Sets the board to blank blocks and gets the initial piece
	GameBoard(ScorePanel s,TetrisMain t)
	{

		tetris = t;
		scorePanel = s;
		playing=true;
		//blank the board
		for (int i=0;i<PLAYABLE_WIDTH;i++) 
			for (int j=0;j<PLAYABLE_HEIGHT;j++) 
				boardArray[i][j] = new NullBlock();

		playerPiece = getNextPiece();

	}

	//blanks the playing board, clears the incoming pieces, gets a new piece
	public void reset()
	{
		for (int i=0;i<PLAYABLE_WIDTH;i++) 
			for (int j=0;j<PLAYABLE_HEIGHT;j++) 
				boardArray[i][j] = new NullBlock();
	
		RandomPiece.Reset();
		waitingPieces.clear();
		playerPiece = getNextPiece();

		playing = true;				
		
	}

	//turns Ghost Piece function on and off
	public void toggleGhostPiece(){ ghostPiece = !ghostPiece; }
	
	//get a Random Piece, add it to the remaining pieces
	//return the piece at the front of the list
	private Piece getNextPiece(){
		while ( waitingPieces.size() < minimumWaitingPieces + 1)
		{
			for(Piece pie : waitingPieces )
			{
				pie.Move(Direction.SOUTH, 3);
				pie.Move(Direction.EAST);
			}
			waitingPieces.add(RandomPiece.Create(new Point(PIECE_START) ) );			
		}

		scorePanel.increaseScore();

		return waitingPieces.remove(0);

	}

	
	//check to see if the all the block positions in a piece
	//already consists of a piece (false) or a blank block (true)
	private boolean checkCollision(Piece pie)
	{
		for(MoveableBlock b : pie.getBlocks() )
		{
			Point p = b.getPosition();
			int x = (int)p.getX();
			int y = (int)p.getY();
			if ( (x<0) || (x>=PLAYABLE_WIDTH) || (y>=PLAYABLE_HEIGHT) ) return true;
			if ( y>0) if (! (boardArray[x][y] instanceof NullBlock) ) return true;
		}
		return false;
	}

	

	

	//check if any rows are full and therefore need to be deleted
	private void checkRow()
	{	
		int numRowsRemoved=0;	
		for(int y=0; y<PLAYABLE_HEIGHT; y++)
		{
			boolean rowFound=true;
			for(int x=0; x<PLAYABLE_WIDTH ; x++)					
				 if( boardArray[x][y] instanceof NullBlock )
				 {
					 rowFound = false;
					 break; //optimization
				 }
			
			if( rowFound ) 
			{
				//System.out.println("delete row " +y);
				for(int i = y; i >0; i--)
					for(int x=0; x<PLAYABLE_WIDTH; x++)
						boardArray[x][i] = boardArray[x][i-1];				
				repaint();
				numRowsRemoved++;
			}

		}
		if ( numRowsRemoved == 3 ) waitingPieces.add(0, new P_bigO( new Point( (int)(FULL_WIDTH/2)-1, -6 )));
		if ( numRowsRemoved >0 ) { scorePanel.rowScore(numRowsRemoved);	tetris.speedUp(numRowsRemoved);}

	}

	
	public void addBigI()
	{
		waitingPieces.add(0, new P_bigI( new Point( (int)(FULL_WIDTH/2)-1, -6 )));
	}

	public void add4dots()
	{
		waitingPieces.add(0, new P_4dots( new Point( (int)(FULL_WIDTH/2)-1, -6 )));
	}


	public void SetMeUpCheat()
	{
		for (int i=1;i<PLAYABLE_WIDTH;i++) 
			for (int j=PLAYABLE_HEIGHT/2;j<PLAYABLE_HEIGHT;j++) 
				boardArray[i][j] = new Block(new Color(250*i/PLAYABLE_WIDTH, 500*j/PLAYABLE_HEIGHT - 230 ,
					250*(PLAYABLE_HEIGHT+PLAYABLE_WIDTH-i-j)/(PLAYABLE_HEIGHT+PLAYABLE_WIDTH)) );
	}

	public void randomizeColors()
	{
		Random r = new Random();

		for (int i=0;i<PLAYABLE_WIDTH;i++) 
			for (int j=0;j<PLAYABLE_HEIGHT;j++) 
				if ( ! (boardArray[i][j] instanceof NullBlock) ) 
					boardArray[i][j] = new Block( new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));		
	}

	
	//try to add a piece to the board
	//if it is too high, you have lost the game
	private boolean addPieceToBoard(Piece pie)
	{
		for(MoveableBlock b : pie.getBlocks() )
		{
			Point p = b.getPosition();
			if((int)p.getY() <= TOO_HIGH) return false;
			boardArray[(int)p.getX()][(int)p.getY()] = new Block(b);
		}
		return true;
	}

	//rotate a piece clockwise, check location
	public void clock()
	{
		playerPiece.RotateClockwise(); //first we rotate
		if (checkCollision(playerPiece))  //if we are overlapping something, try moving left
		{
			playerPiece.Move( Direction.WEST );
			if (checkCollision(playerPiece))  //if that is a bad spot, move right twice
			{
				playerPiece.Move( Direction.EAST,2 );
				if (checkCollision(playerPiece))  //if we still haven't found a good spot, undo everything
				{				
					playerPiece.Move( Direction.WEST );	
					playerPiece.RotateCounterClockwise(); 
				}
			}
		}
	}

	
	//rotate a piece counterclockwise, check location
	public void counter()
	{
		playerPiece.RotateCounterClockwise(); //first we rotate
		if (checkCollision(playerPiece))  //if we are overlapping something, try moving right
		{
			playerPiece.Move( Direction.EAST );
			if (checkCollision(playerPiece))  //if that is a bad spot, move left twice
			{
				playerPiece.Move( Direction.WEST,2 );
				if (checkCollision(playerPiece))  //if we still haven't found a good spot, undo everything
				{				
					playerPiece.Move( Direction.EAST );	
					playerPiece.RotateClockwise(); 
				}
			}
		}
	}


	//while able to drop down, drop the piece all the way to the bottom
	public void hardDrop()
	{
		while ( !down() );
	}	


	/*attempt to move piece down -- if impossible, lock piece into place
	  if piece gets locked, also remove any rows and generate the next piece
          returns true if piece got locked, false normally */
	public boolean down()
	{	
		
		playerPiece.Move(Direction.SOUTH);
		if(checkCollision(playerPiece) )
		{
			playerPiece.Move( Direction.NORTH );
			if(!addPieceToBoard(playerPiece)){playing = false; scorePanel.setHighScore(); return true;}
			else{checkRow(); //now we see if we can remove any rows
				playerPiece = getNextPiece();
				return true;}
		}
		return false;
	}

	
	//move to the right
	//if there is a collision, move back to left
	public void right()
	{
		playerPiece.Move( Direction.EAST );
		if (checkCollision(playerPiece)) playerPiece.Move( Direction.WEST ); 
	}
	
	
	//move to the left
	//if there is a collision, move back to right
	public void left()
	{
		playerPiece.Move( Direction.WEST );
		if (checkCollision(playerPiece)) playerPiece.Move( Direction.EAST ); 
	}

	//draw the individual blocks the piece is made of
	public void drawBlock(Block b, int x, int y, Graphics2D g)
	{
		Rectangle2D.Float colorBlock = new Rectangle2D.Float(x*21, y*21, 20, 20);
		g.setPaint(b.getPicture());
		g.fill(colorBlock);
	}

	//draw the currently moving block
	public void drawPiece( Piece pie, Graphics2D g)
	{
		for(MoveableBlock b : pie.getBlocks() )
		{
			Point p = b.getPosition();
			drawBlock(b, (int)p.getX(), (int)p.getY()+EXTRA_HEIGHT, g);		
		}
	}

	//draw the Ghost piece
	public void drawGhost( Piece pie, Graphics2D g)
	{
		int moves=0;

		pie.Move(Direction.SOUTH);
		while (! checkCollision(pie) ) { pie.Move(Direction.SOUTH); moves++; }
		pie.Move(Direction.NORTH);
		
		if (moves>4) for(MoveableBlock b : pie.getBlocks() )
		{
			b.setPicture( new Color(.3f,.3f,.3f,.9f) );
			Point p = b.getPosition();
			drawBlock(b, (int)p.getX(), (int)p.getY()+EXTRA_HEIGHT, g);		
		}
		
		pie.Move(Direction.NORTH,moves);
	}

	//draw the board
	public void drawBoard(Graphics2D g)
	{
		for(int x=0; x<PLAYABLE_WIDTH; x++)
			for(int y=0; y<PLAYABLE_HEIGHT; y++)
				drawBlock(boardArray[x][y],x,y+EXTRA_HEIGHT,g);
	}


	//draws all the blocks, board and scores
	public void paintComponent(Graphics comp)
	{
		super.paintComponent(comp);
		Graphics2D comp2D = (Graphics2D)comp;

		//a big black box covering up whatever was there before
		Rectangle2D.Float colorBlock = new Rectangle2D.Float(0, 0, 21*FULL_WIDTH, 21*FULL_HEIGHT);
		comp2D.setPaint(Color.BLACK);
		comp2D.fill(colorBlock);

		comp2D.setPaint(Color.green);
		comp2D.draw(new Line2D.Double(0, 21+EXTRA_HEIGHT*21, FULL_WIDTH*21, 21+EXTRA_HEIGHT*21));

		comp2D.drawString("Score: "+Integer.toString(scorePanel.score),  ( (float)FULL_WIDTH )*11 , 50f);
		comp2D.drawString("Level: "+Integer.toString(scorePanel.level),  ( (float)FULL_WIDTH )*11 , 75f);
		comp2D.drawString("Rows: "+Integer.toString(scorePanel.totalRows),  ( (float)FULL_WIDTH )*11 , 100f);
		
		if (scorePanel.highScore >0)
		{		
			//comp2D.drawString("HIGH SCORE",  ( (float)FULL_WIDTH )*7 , 10f);
			comp2D.drawString(scorePanel.name +"  "+ Integer.toString(scorePanel.highScore),  ( (float)FULL_WIDTH )*11 , 15f);
			
		}		

		if (scorePanel.score < 5)
		{		
			//comp2D.drawString("HIGH SCORE",  ( (float)FULL_WIDTH )*7 , 10f);
			comp2D.drawString("Move piece: Left, Right, Down", 20f , 350f);
			comp2D.drawString("Rotate piece: [Z], Up or [X]", 20f , 375f);
			comp2D.drawString("Drop piece: Space", 20f , 400f);

			comp2D.drawString("Music toggle: [M]", 20f , 450f);
			comp2D.drawString("Ghost piece: [G]", 20f , 475f);

			comp2D.drawString("Increase speed: [+ =]", 20f , 525f);
			comp2D.drawString("Pause game: [P]", 20f , 550f);    
		}		


				

		for(int i=waitingPieces.size()-1;i>=0;i-- )
			drawPiece(waitingPieces.get(i),comp2D);

		if (ghostPiece) drawGhost(playerPiece,comp2D);	
		drawPiece(playerPiece,comp2D);	

		drawBoard(comp2D);
	}

}
