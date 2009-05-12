import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Random;

class Ball extends Thread {

	private JPanel box;

	public static final int time_interval = 50;
	public static final int sleep_time=50;   //ms


	private static final int XSIZE = 50;
	private static final int YSIZE = 40;
	private int x = 15;
	private int y = 28;
	private int dx = 2;
	private int dy = 2;
	Network network;

	BufferedImage img = null;	

	private boolean paused=false;

	private static Ball singleton=null;

	public static Ball getBall(JPanel b, Network n)
	{
		if (singleton!=null) return singleton;

		singleton = new Ball(b, n);
		return singleton;
	}
	
	private Ball(JPanel b, Network n) {
		network = n;
		box = b;
		setImg();
		Random r = new Random();
		dx=r.nextInt(4) + 1;
		dy=r.nextInt(3) + 1;
		System.out.println(dx+" "+dy);
	}

	public void setImg()
	{
		try {
		    img = ImageIO.read(new File("cat.jpg"));
		} catch (IOException e) {}
	}

	public void draw() {
		Graphics g = box.getGraphics();
		//g.dispose();
		g.drawImage(img, x-XSIZE/2, y-YSIZE/2, null);
	}

	public void move() {
		if (!box.isVisible())	return;
		//Graphics g = box.getGraphics();
		//g.setXORMode(box.getBackground());
		//g.fillOval(x, y, XSIZE, YSIZE);
		//g.drawImage(img, x, y, null);
		x += dx;
		y += dy;
		Dimension d = box.getSize();
		if (x < 0) {
			x = 0;
			dx = -dx;
		}
		if (x + XSIZE >= d.width) {
			x = d.width - XSIZE;
			dx = -dx;
		}
		if (y < 0) {
			y = 0;
			dy = -dy;
		}
		if (y + YSIZE >= d.height) {
			y = d.height - YSIZE;
			dy = -dy;
		}
		//g.drawImage(img, x, y, null);
		//g.fillOval(x, y, XSIZE, YSIZE);
//		g.dispose();
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

	public void run() {
		try {
			draw();
			for (int i = -3; true; i++) {
				if (!paused) { 
				move();
				if ( (i%time_interval) == 0 ) { network.update(x, y); }
				network.d.repaint();

				}
				draw();
				sleep(sleep_time);
			}
		} catch (InterruptedException e) {}
	}

	public void pause()
	{
		paused= !paused;
		System.out.println(paused);
	}

}
