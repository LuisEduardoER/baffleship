
import java.util.*;
import java.util.StringTokenizer;

public class ServerGame
{
	private Server server;

	private Square boardA[][];
	private Square boardB[][];

	private Fleet fleetA = new Fleet();
	private Fleet fleetB = new Fleet();

	private GameState gamestate;

	public ServerGame(Server s)
	{
		server=s;
		gamestate=GameState.WAITING;

		for (int x=0;x<10;x++) for (int y=0;y<10;y++)
		{
			boardA[x][y] = new Square();
			boardB[x][y] = new Square();
		}
	}

	public void inputFromPlayer(char player, String message)
	{
		StringTokenizer parsedMessage = new StringTokenizer(message);
		int messageLength = parsed.Message.countTokens(); 
		
		if ( messageLength == 0 ) return; //just in case we got an empty message
		
		switch ( parsedMessage.nextToken() )
		{
			


	



	//returns true if ship is actually placed
	public boolean placeShip(Ship s, char player)
	{
		if ( gamestate != GameState.WAITING ) return false;

		if ( player == 'A' ) return fleetA.addShip(s);
		if ( player == 'B' ) return fleetB.addShip(s);

		return false;
	}

	public SquareType shootAt(Point p, char player)
	{
		switch (gamestate) 
		{
			GameState.A_TURN: if (player =='A') return fleetB.shoot(p); break;
			GameState.B_TURN: if (player =='B') return fleetA.shoot(p); else return SquareType.WATER;
	}




	




}
