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


public class Client
{
   private PrintWriter output; // output stream to server
   private BufferedReader input; // input stream from server
   private String message = ""; // message from server
   private String chatServer; // host server for this application
	private int port;	//port # on host
   private Socket client; // socket to communicate with server
	BattleGUI battleGui;
	
	String delims = "[ ]+";


   // initialize chatServer and set up GUI
   public Client( String host , int p , BattleGUI b)
   {
      battleGui = b;

      chatServer = host; // set server to which this client connects
	port = p;
	//runClient();
   } // end Client constructor

   // connect to server and process messages from server
   public void runClient() 
   {
      try // connect to server, get streams, process connection
      {
         connectToServer(); // create a Socket to make connection
         getStreams(); // get the input and output streams
         processConnection(); // process connection
      } // end try
      catch ( EOFException eofException ) 
      {
         displayMessage("\nClient terminated connection" );
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
      displayMessage(  "Attempting connection\n" );

      // create Socket to make connection to server
      client = new Socket( InetAddress.getByName( chatServer ), port );

      // display connection information
      displayMessage(  "Connected to: " + 
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
                System.out.println(tokens[0]);
                
              if(tokens[0].equals("CHAT"))
              {
                     displayMessage(  "\nOpponent: ");
                     for(int i=1; i<tokens.length; i++)
			{ 
                        	displayMessage( tokens[i] +" " );
				if ( i%8  == 0 ) displayMessage( "\n" );

			}
				
              }  
	         if(tokens[0].equals("YOURSHOT"))
	         {  
		            SquareType result = SquareType.parseShip(tokens[1]);
		            if(!result.isShip()){
			            displayMessage(  "\nYou missed");
			            battleGui.yourShotMissed(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
		            }
		            else {
			            displayMessage(  "\nYou hit the " + result.name);
			            battleGui.yourShotHit(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
		            }
	          }
	         if(tokens[0].equals("HISSHOT"))
	         {  
		            SquareType result = SquareType.parseShip(tokens[1]);
		            if(!result.isShip())
		            {
			            displayMessage( "\nOpponent Miss");
			            battleGui.opponentMissShip(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
		            }
		            else 
		            {
			            displayMessage( "\nYour opponent hit your " + result.name);
			            battleGui.opponentHitShip(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
		            }
	         }
	        if(tokens[0].equals("GAMEOVER"))
	        { 
	                if(tokens[1].equals("WIN"))
	                {
	                    displayMessage(  "\nGame Over. You Win!");
	                    battleGui.youWin();
	                }
                   if(tokens[1].equals("LOSE"))
                   {
	                    displayMessage( "\nGame Over. You Lose.");
	                    battleGui.youLose();
	               }
	         }

	       if(tokens[0].equals("SUNK"))
	       {  
	            String sunkShip = "\nYou Sunk the " + tokens[1];
	            displayMessage( sunkShip);
	       }
	
      } while(!tokens[0].equals("EXIT")); 
   } // end method processConnection

   // close streams and socket
   private void closeConnection() 
   {
      displayMessage(  "\nClosing connection" );
 
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
	   String[] tokens = message.split(delims);
            
            if(tokens[0].equals("CHAT")){
                displayMessage(  "\nYou: ");
                 for(int i=1; i<tokens.length; i++) 
                    displayMessage(  tokens[i] +" " );
           }  
  
   } // end method sendData

   // manipulates displayArea in the event-dispatch thread
   private void displayMessage(  final String messageToDisplay )
   {
      SwingUtilities.invokeLater(
         new Runnable()
         {
            public void run() // updates displayArea
            {
               battleGui.displayArea.append( messageToDisplay );
            } // end method run
         }  // end anonymous inner class
      ); // end call to SwingUtilities.invokeLater
   } // end method displayMessage

  
} // end class Client

/**************************************************************************
 * (C) Copyright 1992-2005 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
