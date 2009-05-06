
import java.util.*;
import java.util.StringTokenizer;

public class ServerGame
{
	private Server server;

	private Square boardA[][];
	private Square boardB[][];

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
		StringTokenizer parsedMessage = new StringTokenizer(message);
		int messageLength = parsedMessage.countTokens(); 
		
		if ( messageLength == 0 ) return; //just in case we got an empty message

		String command = parsedMessage.nextToken().toUpperCase();

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

		if ( command.equals("PLACE") ) 
		{

			if ( gamestate != GameState.WAITING ) return false;  //lol too late
			if ( messageLength < 5 ) return; //incomplete command, ignore

			SquareType newShipType;
			int x;
			int y;
			Direction d;
		
			newShipType = SquareType.parseShip( parsedMessage.nextToken() );
			if ( ( newShipType == null ) || (newShipType == SquareType.WATER ) ) return; //invalid ship, ignore
			
			







			server.sendToPlayer('A', "CHAT Player " + player+ " attempted to place a ship: "+message);
			server.sendToPlayer('B', "CHAT Player " + player+ " attempted to place a ship: "+message);
		}


		if ( command.equals("SHOOT") ) 
		{
			server.sendToPlayer('A', "CHAT Player " + player+ " attempted to shoot: "+message);
			server.sendToPlayer('B', "CHAT Player " + player+ " attempted to shoot: "+message);
		}
		

	}

	



	//returns true if ship is actually placed
	public boolean placeShip(Ship s, char player)
	{

	}

/*	public SquareType shootAt(Point p, char player)
	{
		switch (gamestate) 
		{
			GameState.A_TURN: if (player =='A') return fleetB.shoot(p); break;
			GameState.B_TURN: if (player =='B') return fleetA.shoot(p); else return SquareType.WATER;
	}


*/

	




}
