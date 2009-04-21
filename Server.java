// Fig. 24.5: Server.java
// Set up a Server that will receive a connection from a client, send 
// a string to the client, and close the connection.
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Server
{
   private JTextField enterField; // inputs message from user
   private JTextArea displayArea; // display information to user
   private ObjectOutputStream outputA; // output stream to client
   private ObjectInputStream inputA; // input stream from client
   private ObjectOutputStream outputB; // output stream to client
   private ObjectInputStream inputB; // input stream from client
   private ServerSocket server; // server socket
   private Socket connectionA; // connection to client A
   private Socket connectionB; // connection to client B
   private int counter = 1; // counter of number of connections
	private int port;
	private boolean A_PRESENT=false;
	private boolean B_PRESENT=false;

   // set up GUI
   public Server(int p)
   {
	port = p;

      //setVisible( true ); // show window
   } // end Server constructor

   // set up and run server 
   public void runServer()
   {
      try // set up server to receive connections; process connections
      {
         server = new ServerSocket( port, 100 ); // create ServerSocket

         while ( true ) 
         {
            try 
            {
               waitForConnection(); // wait for a connection
               getStreams(); // get input & output streams
               processConnection(); // process connection
            } // end try
            catch ( EOFException eofException ) 
            {
               displayMessage( "\nServer terminated connection" );
            } // end catch
            finally 
            {
               closeConnection(); //  close connection
               counter++;
            } // end finally
         } // end while
      } // end try
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } // end catch
   } // end method runServer

   // wait for connection to arrive, then display connection info
   private void waitForConnection() throws IOException
   {
      System.out.println( "Waiting for connection\n" );

	connectionA = server.accept(); // allow server to accept connection  
	System.out.println( "Connection " + counter + " received from: " +
         connectionA.getInetAddress().getHostName() );

	counter++;

	connectionB = server.accept();        
	System.out.println( "Connection " + counter + " received from: " +
        connectionB.getInetAddress().getHostName() );
      
   } // end method waitForConnection

   // get streams to send and receive data
   private void getStreams() throws IOException
   {
      // set up output stream for objects
      outputA = new ObjectOutputStream( connectionA.getOutputStream() );
      outputA.flush(); // flush output buffer to send header information

      // set up input stream for objects
      inputA = new ObjectInputStream( connectionA.getInputStream() );

	// set up output stream for objects
     outputB = new ObjectOutputStream( connectionB.getOutputStream() );
     outputB.flush(); // flush output buffer to send header information

      // set up input stream for objects
      inputB = new ObjectInputStream( connectionB.getInputStream() );

	System.out.println("getStreams");

   } // end method getStreams

   // process connection with client
   private void processConnection() throws IOException
   {
      String messageA;
      String messageB;
	char A = 'A';
	char B = 'B';
      //sendData( message ); // send connection successful message

      do // process messages sent from client
      { 
         try // read message and display it
         {
		//if (inputA.available() > 0 )
		//{
		       	messageA = ( String ) inputA.readObject(); // read new message
			System.out.println( "\nfrom A: " + messageA ); // display message
			sendData(messageA, B);
		//}

		//if (inputB.available() > 0 )
		//{
		       	messageB = ( String ) inputB.readObject(); // read new message
			System.out.println( "\nfrom B: " + messageB ); // display message
			sendData(messageB, A);
		//}



         } // end try
         catch ( ClassNotFoundException classNotFoundException ) 
         {
            System.out.println( "\nUnknown object type received" );
         } // end catch

      } while ( true );
   } // end method processConnection

   // close streams and socket
   private void closeConnection() 
   {
      System.out.println( "\nTerminating connection\n" );
    

      try 
      {
         outputA.close(); // close output stream
         inputA.close(); // close input stream
         connectionA.close(); // close socket
	 outputB.close(); // close output stream
         inputB.close(); // close input stream
         connectionB.close(); // close socket
      } // end try
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } // end catch
   } // end method closeConnection

   // send message to client
   private void sendData( String message, char x )
   {
      try // send object to client
      {
	if(x == 'A'){
         outputA.writeObject( "client B>> " + message );
         outputA.flush(); // flush output to client
         System.out.println( "\nsending to A>>> " + message );
	}
	if(x == 'B'){
	outputB.writeObject( "client A>>> " + message );
         outputB.flush(); // flush output to client
         System.out.println( "\n sendint to B>>> " + message );
}
      } // end try
      catch ( IOException ioException ) 
      {
         System.out.println( "\nError writing object" );
      } // end catch
   } // end method sendData

   // manipulates displayArea in the event-dispatch thread
   private void displayMessage( final String messageToDisplay )
   {
      SwingUtilities.invokeLater(
         new Runnable() 
         {
            public void run() // updates displayArea
            {
               System.out.println( messageToDisplay ); // append message
            } // end method run
         } // end anonymous inner class
      ); // end call to SwingUtilities.invokeLater
   } // end method displayMessage


} // end class Server

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
