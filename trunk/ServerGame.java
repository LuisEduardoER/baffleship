
import java.util.*;

public class ServerGame
{
	private Server server;

	private Square boardA[][];
	private Square boardB[][];

	private java.util.List<Ship> A_Ships = new ArrayList<Ship>();
	private java.util.List<Ship> B_Ships = new ArrayList<Ship>();

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

	




}
