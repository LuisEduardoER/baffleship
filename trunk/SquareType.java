
//john gaetano


import java.awt.*;
import java.util.Random;
import javax.swing.ImageIcon;


public enum SquareType
{

	WATER("water",false,1,"pictures/sea.png","pictures/sea.png","pictures/sea.png",
				"pictures/sea.png","pictures/sea.png","pictures/sea.png"),

	CARRIER("Carrier",true,5,"pictures/ship4-4-r.png","pictures/ship4-3-r.png","pictures/ship4-1-r.png",
				"pictures/ship4-4.png","pictures/ship4-3.png","pictures/ship4-1.png"),
	BSHIP("Baffleship",true,4,"pictures/ship3-3-r.png","pictures/ship3-2-r.png","pictures/ship3-1-r.png",
				"pictures/ship3-3.png","pictures/ship3-2.png","pictures/ship3-1.png"),
	CRUISER("Cruiser",true,3,"pictures/ship3-3-r.png","pictures/ship3-2-r.png","pictures/ship3-1-r.png",
				"pictures/ship3-3.png","pictures/ship3-2.png","pictures/ship3-1.png"),
	SUB("Submarine", true,3,"pictures/ship2-2-r.png","pictures/ship2-3-r.png","pictures/ship2-1-r.png",
				"pictures/ship2-2.png","pictures/ship2-3.png","pictures/ship2-1.png"),
	DESTROYER("Destroyer",true,2,"pictures/ship3-3-r.png","pictures/ship3-2-r.png","pictures/ship3-1-r.png",
				"pictures/ship3-3.png","pictures/ship3-2.png","pictures/ship3-1.png");

	public final String name;
	public final boolean ship;
	public final int length;   //if its a ship, how long is the ship
	
	//H for horizontal, V for vertical
	public final String backH;
	public final String middleH;
	public final String frontH;
	public final String backV;
	public final String middleV;
	public final String frontV;

	//constructor
	SquareType(String n, boolean s, int l, String bh, String mh, String fh, String bv, String mv, String fv )
	{
		name=n;
		ship = s;
		length = l;
		backH =bh;
		middleH =mh;
		frontH=fh;
		backV=bv;
		middleV=mv;
		frontV=fv;
	}

	
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
