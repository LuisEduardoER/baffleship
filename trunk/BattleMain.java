import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import javax.swing.border.*;
import  java.io.*;

public class BattleMain extends JFrame
{


		BattleMain(){
			Toolkit toolkit = Toolkit.getDefaultToolkit();  
			Dimension screenSize = toolkit.getScreenSize();


			JFrame frame = new JFrame("Baffleship!");		
			BattleGUI menu = new BattleGUI();
			frame.setContentPane(menu.createContentPane());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//com.sun.awt.AWTUtilities.setWindowOpacity(frame, 0.70f);
			frame.setSize(500,800);

			//place frame in middle of screen
			int x = (screenSize.width - frame.getWidth()) / 2;  
			int y = (screenSize.height - frame.getHeight()) / 2;  
	 		frame.setLocation(x, y); 
			//frame.setUndecorated(true);
			//com.sun.awt.AWTUtilities.setWindowOpaque(frame, false);
			frame.setVisible(true);

			Client client = new Client("127.0.0.1", 44771, menu);
			menu.setClient(client);
			client.runClient();
		}
	
		
	public static void main(String[] args) {
		BattleMain b = new BattleMain();
	}
}
