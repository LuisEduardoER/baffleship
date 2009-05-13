//BlackBox.java

import java.util.Random;

public class BlackBox
{  

	private int numBaffles, width, height;
	private Baffle baffles[][];

	private Bullet convertGuessToBullet(int g)
	{
		Bullet b; 

		if (g < height)	{ b = new Bullet(-1,height-g-1,Direction.EAST); }
		else if (g < height + width) { b = new Bullet(g-height,-1,Direction.SOUTH); }
		else if (g < 2*height + width) { b = new Bullet(width,g-height-width,Direction.WEST); } 
		else { b = new Bullet(2*height+2*width-g-1,height,Direction.NORTH); }

		return b;
	}


	private int convertBulletToGuess(Bullet b)
	{
		if (b.getX() < 0) return height - b.getY() - 1;
		if (b.getY() < 0) return height + b.getX();
		if (b.getX() >= width) return height + width + b.getY();
		if (b.getY() >= height) return 2*height + 2*width - b.getX() - 1;
		
		//else the bullet is still inside the box
		return -1;
	}
	
	private boolean insideBox(Bullet b)
	{
		return (convertBulletToGuess(b) == -1);
	}


	//constructor
	BlackBox(int widthWanted, int heightWanted, int numBafflesWanted)
	{
		//error correction, dimensions must be positive
		if (widthWanted<1) width=1; else width=widthWanted;
		if (heightWanted<1) height=1; else height=heightWanted;

		//for performance reasons we aren't allowing more than half the box to be baffles
		//you shouldn't want that many anyway
                if (numBafflesWanted*2>width*height) numBafflesWanted=width*height/2;   

		//fill field with "non-baffles"
		baffles = new Baffle[width][height];  
		for (int x=0;x<width;x++) for (int y=0;y<height;y++) baffles[x][y] = Baffle.NONE; 
	
		//now place the desired number of baffles
		Random r = new Random();
		while (numBaffles<numBafflesWanted)
		{
			int x=r.nextInt(width);
			int y=r.nextInt(height);
			System.out.println("Trying to place random baffle at: "+x+","+y+"...");
			if (baffles[x][y] == Baffle.NONE)
			{
				baffles[x][y]= ( r.nextInt(2)==0 ) ? Baffle.LEFT : Baffle.RIGHT;
				System.out.println("Baffle placed!");
				numBaffles++;
			}
		}
	}

	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getNumBaffles() { return numBaffles; }

	public Baffle getBaffleAtPosition(int x, int y)
	{ 
		//bad input = "no baffle here". It makes sense, in a way.
		if ( (x<0) || (x>=width) || (y<0) || (y>=height) ) return Baffle.NONE;
		
		return baffles[x][y];
	}

	
	//returns true if success, false for failure
	public boolean setBaffleAtPosition(int x, int y, Baffle b)
	{ 
		//bad input = return false = failure
		if ( (x<0) || (x>=width) || (y<0) || (y>=height) ) return false;
		
		//if the user isn't actually changing the baffle, just leave;
		if (baffles[x][y]==b) return true;

		//must keep count correct if we are adding or removing a baffle
		//changing baffle from left to right or vice versa doesn't change count of course
		if (baffles[x][y] == Baffle.NONE ) numBaffles++;
		else if (b == Baffle.NONE ) numBaffles--;
		
		//now set the baffle
		baffles[x][y]=b;
		return true;
	}


	public int shootLaser(int g)
	{
		//check for illegal guesses
		if (g < 0) return -1;  
		if (g >= 2*width + 2 *height) return -1;

		Bullet b = convertGuessToBullet(g);
		b.moveBullet();

		while( insideBox(b) )
		{
			b.hitBaffle(baffles[b.getX()][b.getY()]);
			b.moveBullet();
		}

		return convertBulletToGuess(b);
	}

}   








