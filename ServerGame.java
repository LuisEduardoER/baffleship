
import java.util.*;
import java.util.StringTokenizer;
import java.awt.Point;

public class ServerGame
{
	private Server server;

	//track what squares have been shot at
	private boolean boardA[][] = new boolean[10][10];
	private boolean boardB[][] = new boolean[10][10];

	private Fleet fleetA = new Fleet();
	private Fleet fleetB = new Fleet();

	boolean readyA =false;
	boolean readyB =false;

	private GameState gamestate;

	public ServerGame(Server s)
	{
		server=s;
		gamestate=GameState.WAITING;

		/*for (int x=0;x<10;x++) for (int y=0;y<10;y++)
		{
			boardA[x][y] = new Square();
			boardB[x][y] = new Square();
		}*/
	}

	public void inputFromPlayer(char player, String message)
	{
		StringTokenizer tokenizedMessage = new StringTokenizer(message.toUpperCase() );
		int messageLength = tokenizedMessage.countTokens(); 
		
		if ( messageLength == 0 ) return; //just in case we got an empty message

		String command = tokenizedMessage.nextToken();

		//we cant use switch on strings, sadly

		System.out.println("command from "+player+" is "+command);

		if ( command.equals("CHAT") )
		{
			if ( messageLength < 2 ) return; //empty chats not allowed

			if (player == 'A') server.sendToPlayer('B', message);
			if (player == 'B') server.sendToPlayer('A', message);

			return;
		}
		
		if ( command.equals("READY") ) 
		{
			if ( (player == 'A') && ( fleetA.numShips() == 5 ) ) readyA =true; 
			if ( (player == 'B') && ( fleetB.numShips() == 5 ) ) readyB =true;

			if ( ( gamestate == GameState.WAITING ) && readyA && readyB ) gamestate = GameState.TURN_A;
		}


		/* 
		  	 PLACE BSHIP 4 5 EAST
		 */
		if ( command.equals("PLACE") ) 
		{
			if ( gamestate != GameState.WAITING ) return;  //lol too late

			if ( messageLength < 5 ) return; //incomplete command, ignore

			SquareType newShipType;
			int x;
			int y;
			Direction dir;
			Ship newShip;		
		
			newShipType = SquareType.parseShip( tokenizedMessage.nextToken() );
			if ( ( newShipType == null ) || (newShipType.isWater() ) ) return; //invalid ship, ignore
			
			try { x= Integer.parseInt( tokenizedMessage.nextToken() ); } catch (NumberFormatException e) { return; }

			try { y= Integer.parseInt( tokenizedMessage.nextToken() ); } catch (NumberFormatException e) { return; }

			dir = Direction.parseDirection( tokenizedMessage.nextToken() );
			if ( dir == null ) return; //not a direction


			//finally we can make a ship
			newShip = new Ship(newShipType, new Point(x,y), dir);

			//but the ship might extend off the board, in which case we stop
			if ( newShip.outOfBounds(0,0,9,9) ) return;
		
			//else try to add the ship to the appropriate fleet
			//if the fleet rejects it, too bad 
			if (player == 'A') fleetA.addShip(newShip); 
			if (player == 'B') fleetB.addShip(newShip);

			return;
		}


		if ( command.equals("SHOOT") ) 
		{
			if ( messageLength < 3 ) return; //incomplete command, ignore

			int x;
			int y;
			try { x= Integer.parseInt( tokenizedMessage.nextToken() ); } catch (NumberFormatException e) { return; }
			try { y= Integer.parseInt( tokenizedMessage.nextToken() ); } catch (NumberFormatException e) { return; }

			if ( (x < 0) || (x > 9) || (y < 0) || (y > 9) ) return;  //cant shoot off the board

			//cant shoot when it isn't your turn	
			if ( (gamestate == GameState.WAITING ) || (gamestate == GameState.GAMEOVER ) ) return;

			if (gamestate == GameState.TURN_A)
			{
				if (player !='A') return; //cant shoot when it isn't your turn
			
				SquareType result = fleetB.shoot(new Point(x,y) );
		
				server.sendToPlayer('A', "YOURSHOT "+result+" "+x+" "+y);
				server.sendToPlayer('B', "HISSHOT "+result+" "+x+" "+y);
					
				if ( boardB[x][y] ) gamestate = GameState.TURN_B; else boardB[x][y]=true;

				return;
			}


			if (gamestate == GameState.TURN_B)
			{
				if (player !='B') return; //cant shoot when it isn't your turn
		
				SquareType result = fleetA.shoot(new Point(x,y) );
			
				server.sendToPlayer('B', "YOURSHOT "+result+" "+x+" "+y);
				server.sendToPlayer('A', "HISSHOT "+result+" "+x+" "+y);
					
				if ( boardA[x][y] ) gamestate = GameState.TURN_A; else boardA[x][y]=true;

				return;
			}

		}
		

	}


}
