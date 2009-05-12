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

	public static final int time_interval = 10;
	public static final int sleep_time=10;   //ms


	private static final int XSIZE = 10;
	private static final int YSIZE = 10;
	private int x = 0;
	private int y = 0;
	private int dx = 2;
	private int dy = 2;
	Network network;

	BufferedImage img = null;
	

	private boolean killed=false;

	
	public Ball(JPanel b, Network n) {
		network = n;
		box = b;
		setImg();
		Random r = new Random();
		dx=r.nextInt(7) + 1;
		dy=r.nextInt(7) + 1;
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
		//g.fillOval(x, y, XSIZE, YSIZE);
		g.drawImage(img, x, y, null);
		g.dispose();
	}

	public void move() {
		if (!box.isVisible())
		return;
		Graphics g = box.getGraphics();
		g.setXORMode(box.getBackground());
		//g.fillOval(x, y, XSIZE, YSIZE);
		g.drawImage(img, x, y, null);
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
		g.drawImage(img, x, y, null);
		//g.fillOval(x, y, XSIZE, YSIZE);
		g.dispose();
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
			for (int i = 0; i <= 5000; i++) {
				move();
				network.d.repaint();
				draw();
				if ( (i%time_interval) == 0 ) network.update(x, y);
				if (killed) break;
				sleep(sleep_time);
			}
		} catch (InterruptedException e) {}
	}

	public void dieNowPlease()
	{
		killed=true;
	}

}
