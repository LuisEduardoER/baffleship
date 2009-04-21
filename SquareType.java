
//john gaetano


import java.awt.*;


public enum SquareType
{

	WATER(Color.BLUE, Color.CYAN, false),

	CARRIER(Color.RED, Color.ORANGE, true),
	BSHIP(Color.GREEN, Color.YELLOW, true),
	CRUISER(Color.WHITE, Color.GRAY, true),
	SUB(Color.MAGENTA, Color.PINK, true),
	DESTROYER(Color.PINK, Color.RED, true);

	public final Color picture;    	//this could be a picture of water, or part of a ship...
	public final Color endPicture;   //alternate picture for endpieces
	public final boolean ship;

	//constructor
	SquareType(Color p, Color ep, boolean s) {  picture = p; endPicture=ep; ship = s; }	
	
	public boolean isShip() { return ship; }
	public boolean isWater() { return !ship; }
		
	//setters not needed.  our enums are final.

}
