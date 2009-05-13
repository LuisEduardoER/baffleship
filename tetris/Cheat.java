
//Cheat.java

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class Cheat
{

	private java.util.List<Integer> cheatCode;  //the code the player is trying to match
	private java.util.List<Integer> keys;	  //for a correct in-progress match of the code


	//constructor takes variable number of arguments and puts them in cheatCode list
	public Cheat(int... c)
	{
		cheatCode = new ArrayList<Integer>();
		keys = new ArrayList<Integer>();
		
		for (int i =0; i<c.length ;i++)	cheatCode.add(c[i]);
	}

	//takes the most recently enetered keystroke
	//return true if the player has just completed the cheat
	//return false otherwise
	public boolean nextKey(int k)
	{
		if ( cheatCode.get( keys.size() ) == k )
		{
			keys.add(k);
			if  ( keys.size() == cheatCode.size() )
			{
				keys.clear();
				return true;
			}
			else return false;
		}
	
		// else the player has messed up and has to start over

		keys.clear();
		if ( cheatCode.get(0) == k ) keys.add(k);  //if the incorrect keystroke is the start of the sequence we'll use it
		return false;
	}







}


		
