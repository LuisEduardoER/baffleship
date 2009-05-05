
//Ship.java

import java.awt.*;
import java.util.*;


public class Fleet
{
	private java.util.List<Ship> ships = new ArrayList<Ship>();


public Fleet()
	{;		
	}

//returns true if ship is actually added or repositioned
public boolean addShip(Ship newShip)
	{
		//cant add a ship made out of water
		if (newShip.shipType == SquareType.WATER) return false;

		//cant add a ship of the same kind as already exists
		for(Ship s : ships) if (newShip.shipType == s.shipType ) return false;

		//can't add a ship that collides with an existing ship
		for(Ship s : ships) 
		


	}


//returns true if its a hit, false for a miss
//but really you should probably only be calling this if you know its a hit
//deletes the hit square
public boolean shoot(Point p)
	{
		for (int i=0; i< squares.size() ; i++) if ( squares.get(i).location.equals( p ) )
		{			
			squares.remove(i);
			return true;
		}
		//else
		return false;
	}	

public boolean isSunk() { return squares.isEmpty();  }



}
