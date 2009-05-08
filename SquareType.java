
//john gaetano


import java.awt.*;
import java.util.Random;


public enum SquareType
{

	WATER("water",Color.BLUE, Color.CYAN, false,1),

	CARRIER("Carrier",Color.RED, Color.ORANGE, true,5),
	BSHIP("Baffleship",Color.GREEN, Color.YELLOW, true,4),
	CRUISER("Cruiser",Color.WHITE, Color.GRAY, true,3),
	SUB("Submarine",Color.MAGENTA, Color.PINK, true,3),
	DESTROYER("Destroyer",Color.PINK, Color.RED, true,2);

	public final String name;
	public final Color picture;    	//this could be a picture of water, or part of a ship...
	public final Color endPicture;   //alternate picture for endpieces
	public final boolean ship;
	public final int length;   //if its a ship, how long is the ship

	//constructor
	SquareType(String n, Color p, Color ep, boolean s, int l) {  name=n; picture = p; endPicture=ep; ship = s; length = l;}	
	
	public boolean isShip() { return ship; }
	public boolean isWater() { return !ship; }
		
	//setters not needed.  our enums are final.

	public static SquareType parseShip(String s)
	{
		try { return valueOf(s.toUpperCase() ); } 
			catch (Exception e) { return null; }
        }

	public static SquareType randomShip()
	{

		switch ( (new Random()).nextInt(5) )
		{
			case 0: return CARRIER;
			case 1: return BSHIP;
			case 2: return CRUISER;
			case 3: return SUB;
			default: return DESTROYER; //needed to compile but should never happen
		}			

        }


}
