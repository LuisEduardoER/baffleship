
//Bullet.java
class Bullet
{
	private int xPos;
	private int yPos;
	private Direction dir;

	public int getX() { return xPos; }
	public int getY() { return yPos; }
	public Direction getD() { return dir; }   //not actually used anyplace

	//constructor
	Bullet(int x, int y, Direction d) 
	{
		xPos=x;
		yPos=y;
		dir=d;
	}


	public void hitBaffle(Baffle b)
	{
		if (b==Baffle.NONE) return;
		
		if (b==Baffle.LEFT)   //     Looks like:   \
		{
			switch (dir) {
				case NORTH: dir=Direction.WEST; break;
				case EAST: dir=Direction.SOUTH; break;
				case SOUTH: dir=Direction.EAST; break;
				case WEST: dir=Direction.NORTH; break;
				default: System.out.println("no such direction"); break;
				}
			return;
		}
	
		if (b==Baffle.RIGHT) //     Looks like:   /
		{
			switch (dir) {
				case NORTH: dir=Direction.EAST; break;
				case EAST: dir=Direction.NORTH; break;
				case SOUTH: dir=Direction.WEST; break;
				case WEST: dir=Direction.SOUTH; break;
				default: System.out.println("no such direction"); break;
				}
			return;
		}

		System.out.println("no such baffle");
	}

	public void moveBullet()
	{
		switch (dir) {
			case NORTH: yPos--; break;
			case EAST: xPos++; break;
			case SOUTH: yPos++; break;
			case WEST: xPos--; break;
			default: System.out.println("no such direction"); break;
			}		
	}
}

