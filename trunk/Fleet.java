
//Ship.java

import java.awt.*;
import java.util.*;


public class Fleet
{
	public java.util.List<Ship> ships = new ArrayList<Ship>();

//returns true if ship is actually added or repositioned
public boolean addShip(Ship newShip)
	{
		//cant add a ship made out of water
		if ( newShip.shipType.isWater() ) return false;

		//cant add a ship of the same kind as already exists
		for(Ship s : ships) 
			if (newShip.shipType == s.shipType ) return false;

		//can't add a ship that collides with an existing ship
		for(Ship s : ships)
			for (int i=0; i<newShip.shipType.length; i++)
				if ( s.collides( Direction.Move(newShip.startLocation,newShip.facing,i) ) )
					return false;

		//finally we can add the ship
		ships.add(newShip);
		return true;
	}

//returns WATER if you hit water or else returns the ship type on a hit
//there is no protection against shooting the same square many times
//that can be taken care of some other place
public SquareType shoot(Point p)
	{
		for (Ship s : ships)
			if ( s.shoot(p) ) return s.shipType;

		return SquareType.WATER;
	}	


public boolean isSunk()
	{
		for (Ship s : ships)
			if ( ! (s.isSunk() )) return false;
		
		return true;
	}


public int numShips() { return ships.size(); }

}
