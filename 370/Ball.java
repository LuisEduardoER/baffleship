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

class Ball extends Thread {

	private JPanel box;

	private static final int XSIZE = 10;
	private static final int YSIZE = 10;
	private int x = 0;
	private int y = 0;
	private int dx = 2;
	private int dy = 2;
	Network network;

	BufferedImage img = null;
	
	
	public Ball(JPanel b, Network n) {
		network = n;
		box = b;
		setImg();
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
			for (int i = 1; i <= 1000; i++) {
				move();
				network.update(x, y);
				sleep(3);
				draw();
			}
		} catch (InterruptedException e) {}
	}
}
