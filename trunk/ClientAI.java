// Fig. 24.7: Client.java
// Client that reads and displays information sent from a Server.
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.net.*;
import java.io.*;
import java.util.Random;
import java.awt.Point;
import java.util.*;


public class ClientAI
{
   private PrintWriter output; // output stream to server
   private BufferedReader input; // input stream from server
   private String message = ""; // message from server
	private String chatServer; // host server for this application
	private int port;	//port # on host
   	private Socket client; // socket to communicate with server

	private boolean shots[][] = new boolean[10][10];
	private java.util.List<Point> shotQ = new ArrayList<Point>();
	int minQsize=17;
	
	Random rrr = new Random(); 
	
	String delims = "[ ]+";

	public static void main(String[] args)
	{
			String host="127.0.0.1";  //default to localhost
			try { host= new String(args[0] ); } catch (Exception e) {;}

			int port = 44771; //default port
			try { port= Integer.parseInt(args[1]); } catch (Exception e) {;}

        		ClientAI client = new ClientAI( host, port); // use args to connect
			client.runClient();
	}

	private void displayMessage(Color c, String s)
	{
		System.out.println(s);
	}



   public ClientAI( String host , int p)
   {
	chatServer = host; 
	port = p;	
   } 

   // connect to server and process messages from server
   public void runClient() 
   {
      try // connect to server, get streams, process connection
      {
         connectToServer(); // create a Socket to make connection
         getStreams(); // get the input and output streams

	placePieces();

         processConnection(); // process connection
      } // end try
      catch ( EOFException eofException ) 
      {
         displayMessage(Color.black,  "\nClient terminated connection" );
      } // end catch
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } // end catch
      finally 
      {
         closeConnection(); // close connection
      } // end finally
   } // end method runClient

   // connect to server
   private void connectToServer() throws IOException
   {      
      displayMessage( Color.black, "Attempting connection\n" );

      // create Socket to make connection to server
      client = new Socket( InetAddress.getByName( chatServer ), port );

      // display connection information
      displayMessage( Color.black, "Connected to: " + 
         client.getInetAddress().getHostName() );
   } // end method connectToServer

   // get streams to send and receive data
   private void getStreams() throws IOException
   {
      // set up output stream for objects
      output = new PrintWriter(client.getOutputStream() , true); 
      
      // set up input stream for objects
      input =  new BufferedReader( new InputStreamReader( client.getInputStream() ) );

   } // end method getStreams

   // process connection with server
   private void processConnection() throws IOException
   {
     String[] tokens;
      do // process messages sent from server
      {      
            message =  input.readLine(); // read new message
            tokens = message.split(delims);
            
          if(tokens[0].equals("CHAT")){ displayMessage( Color.green, "\nOpponent: "+message); }  

	 if(tokens[0].equals("YOURSHOT"))
	 {  
		SquareType result = SquareType.parseShip(tokens[1]);
		shots[Integer.parseInt(tokens[2])] [Integer.parseInt(tokens[3])] =true;
		
		if(result.isWater()){
			displayMessage( Color.red, "\nYou missed");
		}
		else {
				minQsize--;
			displayMessage( Color.red, "\nYou hit the " + result.name);
			if (rrr.nextInt(2) ==0) sendData( "CHAT I HIT UR MOM");
			Point tempP = new Point( Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]) );
			tempP=Direction.Move(tempP, Direction.NORTH, 1);
			shotQ.add( tempP );
			tempP=Direction.Move(tempP, Direction.EAST, 1);
			pruneQ(tempP);
			tempP=Direction.Move(tempP, Direction.SOUTH, 1);
			shotQ.add( tempP );
			tempP=Direction.Move(tempP, Direction.SOUTH, 1);
			pruneQ(tempP);
			tempP=Direction.Move(tempP, Direction.WEST, 1);
			shotQ.add( tempP );
			tempP=Direction.Move(tempP, Direction.WEST, 1);
			pruneQ(tempP);
			tempP=Direction.Move(tempP, Direction.NORTH, 1);
			shotQ.add( tempP );
			tempP=Direction.Move(tempP, Direction.NORTH, 1);
			pruneQ(tempP);
			}
	}
	
	if(tokens[0].equals("YOURTURN")) Shoot();
	
	
	 if(tokens[0].equals("HISSHOT")){  
		SquareType result = SquareType.parseShip(tokens[1]);
		if(result.isWater())
		{
			displayMessage( Color.red, "\nOpponent Miss");
		if (rrr.nextInt(10) ==0) sendData( "CHAT HAHA U SUX");
		}
		else
		{
			displayMessage( Color.red, "\nYour opponent hit your " + result.name);
		if (rrr.nextInt(3) ==0) sendData( "CHAT OMG U HAX IMA GONNA GET U BANED");
		}
		
		Shoot();


	   }
	   if(tokens[0].equals("SUNK"))
	{  
	    String sunkShip = "\nYou Sunk the " + tokens[1];
	    displayMessage(Color.red, sunkShip);
	sendData( "CHAT I SUNK YOUR MOM ");
	}

	//now possibly make one or more random comments
	if (rrr.nextInt(50) ==0) sendData( "CHAT WASSSSSSSSSSSUP");
	if (rrr.nextInt(60) ==0) sendData( "CHAT Headline: Gang violence claims lives of five nerds in Henson 105");
	if (rrr.nextInt(100) ==0) sendData( "CHAT xkcdsucks dot blogspot dot com");
	if (rrr.nextInt(80) ==0) sendData( "CHAT IM CHARGIN MAH LAZERS");
	if (rrr.nextInt(80) ==0) sendData( "CHAT Cough if you have swine flu");
	if (rrr.nextInt(80) ==0) sendData( "CHAT ITS OVER 9000");
	if (rrr.nextInt(80) ==0) sendData( "CHAT random is as random does");

	
      } while(! (tokens[0].equals("GAMEOVER"))); 
	  
	  sendData( "CHAT DIAF loser");


   } // end method processConnection

   // close streams and socket
   private void closeConnection() 
   {
      displayMessage( Color.red, "\nClosing connection" );
 
      try 
      {
         output.close(); // close output stream
         input.close(); // close input stream
         client.close(); // close socket
      } // end try
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } // end catch
   } // end method closeConnection

   // send message to server
   public void sendData( String message )
   {
	output.flush();
	try { Thread.sleep(800); } catch (Exception e) {;}
        output.println(message);
	output.flush();
	System.out.println("Sending: "+message);
	try { Thread.sleep(800); } catch (Exception e) {;}
  
   } // end method sendData


private void placePieces()
{
	Fleet myFleet=new Fleet();
	Ship tempShip;

	//fill up a fleet	
	while (myFleet.numShips() <5)
	{
		tempShip = new Ship(SquareType.randomShip(), randPoint(), randDir() );
		if ( ! tempShip.outOfBounds(0,0,9,9) )  myFleet.addShip( tempShip );
	}

	//now send the fleet
	for(Ship s : myFleet.ships) sendData("PLACE " + s.shipType + " " + (int)s.startLocation.getX() + " " + (int)s.startLocation.getY() + " " + s.facing);

	Shoot(); //can't hurt
}

private Point randPoint()
{
	return new Point( rrr.nextInt(10) , rrr.nextInt(10) );
}

private Direction randDir()
{
	return Direction.intToDir(rrr.nextInt(4));
}

private void Shoot()
{
	if ( shotQ.size()<minQsize ) shotQ.add( new Point(rrr.nextInt(10),rrr.nextInt(10)  ));

	Point tempP=shotQ.remove(rrr.nextInt(shotQ.size()));
	int x=(int)tempP.getX();
	int y=(int)tempP.getY();
	
	if ( (x<0)||(y<0)||(x>9)||(y>9)) { Shoot(); return; }
	if (shots[x][y]) { Shoot(); return; }
	
	sendData( "SHOOT " + x  + " " + y  );
}

private void pruneQ(Point p)
{
	for(int i=0;i<shotQ.size();i++) if (shotQ.get(i).equals(p)) shotQ.remove(i);
}

  
} // end class Client

