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


public class ClientAI
{
   private PrintWriter output; // output stream to server
   private BufferedReader input; // input stream from server
   private String message = ""; // message from server
	private String chatServer; // host server for this application
	private int port;	//port # on host
   	private Socket client; // socket to communicate with server
	BattleGUI battleGui;
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



   // initialize chatServer and set up GUI
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
            
          if(tokens[0].equals("CHAT")){
                displayMessage( Color.green, "\nOpponent: ");
                 for(int i=1; i<tokens.length; i++) 
                    displayMessage( Color.black, tokens[i] +" " );
           }  
	 if(tokens[0].equals("YOURSHOT")){  
		SquareType result = SquareType.parseShip(tokens[1]);
		if(!result.isShip()){
			displayMessage( Color.red, "\nYou missed");
			battleGui.yourShotMissed(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
		}
		else {
			displayMessage( Color.red, "\nYou hit the " + result.name);
			battleGui.yourShotHit(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
		}
	  }
	 if(tokens[0].equals("HISSHOT")){  
		SquareType result = SquareType.parseShip(tokens[1]);
		if(!result.isShip()){
			displayMessage( Color.red, "\nOpponent Miss");
			battleGui.opponentMissShip(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
		}
		else {
			displayMessage( Color.red, "\nYour opponent hit your " + result.name);
			battleGui.opponentHitShip(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
		}
	    if(tokens[0].equals("GAMEOVER")){  
	        if(tokens[1].equals("WIN")){
	         displayMessage( Color.red, "\nYGame Over. You Win!");
	         battleGui.youWin();
	        }
           if(tokens[1].equals("LOSE"))
	         displayMessage( Color.red, "\nYGame Over. You Lose.");
	          battleGui.youLose();
	        }
	   }
	   if(tokens[0].equals("SUNK")){  
	    String sunkShip = "\nYou Sunk the " + tokens[1];
	    displayMessage(Color.red, sunkShip);
	       }
	
      } while(!tokens[0].equals("EXIT")); 
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
         output.print(message);
	output.flush();
	System.out.println("Sending: "+message);
  
   } // end method sendData


private void placePieces()
{
	Fleet myFleet=new Fleet();
	Ship tempShip;

	//fill up a fleet	
	while (myFleet.numShips() <5)
	{
		myFleet.addShip( new Ship(SquareType.CARRIER, randPoint(), randDir() ) );
		myFleet.addShip( new Ship(SquareType.BSHIP, randPoint(), randDir() ) );
		myFleet.addShip( new Ship(SquareType.CRUISER, randPoint(), randDir() ) );
		myFleet.addShip( new Ship(SquareType.SUB, randPoint(), randDir() ) );
		myFleet.addShip( new Ship(SquareType.DESTROYER, randPoint(), randDir() ) );
	}

	//now send the fleet
	for(Ship s : myFleet.ships) sendData("PLACE " + s.shipType + " " + s.startLocation.getX() + " " + s.startLocation.getY() + " " + s.facing);

	sendData("READY");
}

private Point randPoint()
{
	return new Point( rrr.nextInt(10) , rrr.nextInt(10) );
}

private Direction randDir()
{
	return Direction.intToDir(rrr.nextInt(4));
}


  
} // end class Client
