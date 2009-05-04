
//john gaetano


import java.awt.*;


public enum SquareType
{

	WATER(Color.BLUE, Color.CYAN, false,1),

	CARRIER(Color.RED, Color.ORANGE, true,5),
	BSHIP(Color.GREEN, Color.YELLOW, true,4),
	CRUISER(Color.WHITE, Color.GRAY, true,3),
	SUB(Color.MAGENTA, Color.PINK, true,3),
	DESTROYER(Color.PINK, Color.RED, true,2);

	public final Color picture;    	//this could be a picture of water, or part of a ship...
	public final Color endPicture;   //alternate picture for endpieces
	public final boolean ship;
	public final int length;   //if its a ship, how long is the ship

	//constructor
	SquareType(Color p, Color ep, boolean s, int l) {  picture = p; endPicture=ep; ship = s; length = l}	
	
	public boolean isShip() { return ship; }
	public boolean isWater() { return !ship; }
		
	//setters not needed.  our enums are final.

}
