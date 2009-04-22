
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
import java.io.StringBufferInputStream;
import java.net.*;
import java.io.*;



public class Server
{
   private JTextField enterField; // inputs message from user
   private JTextArea displayArea; // display information to user
   private PrintWriter outputA; // output stream to client
   private BufferedReader inputA; // input stream from client
   private PrintWriter outputB; // output stream to client
   private BufferedReader inputB; // input stream from client
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

      counter++;
      
   } // end method waitForConnection

   // get streams to send and receive data
   private void getStreams() throws IOException
   {

      // set up output stream for objects
      outputA = new PrintWriter(connectionA.getOutputStream(), true);
	outputA.flush();
      // set up input stream for objects
      inputA =  new BufferedReader( new InputStreamReader( connectionA.getInputStream() ) );

	// set up output stream for objects
     outputB = new PrintWriter(connectionB.getOutputStream(), true);
outputB.flush();
      // set up input stream for objects
      inputB  =  new BufferedReader( new InputStreamReader( connectionB.getInputStream() ) );

	System.out.println("finished getStreams");

   } // end method getStreams

   // process connection with client
   private void processConnection() throws IOException
   {
      char messageA;
      char messageB;
	char A = 'A';
	char B = 'B';

	System.out.println("starting procon");

      while(true) // process messages sent from client
      { 

		if (inputA.ready() )
		{
			System.out.println("1111");

		       	messageA = (char) inputA.read(); // read new message
			System.out.println("2222");
			System.out.println( "\nfrom A: " + messageA ); // display message
			System.out.println("3333");
			sendData(messageA, B);
			System.out.println("4444");
		} 
//else System.out.println("A not rdy");

		if (inputB.ready() )
		{
						System.out.println("B ready");

		       	messageB = (char) inputB.read(); // read new message
			System.out.println( "\nfrom B: " + messageB ); // display message
			sendData(messageB, A);
		} //else System.out.println("B not rdy");



      } 
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
   private void sendData( char message, char x )
   {

	if(x == 'A'){
         outputA.println( "client B>> " + message );
         outputA.flush(); // flush output to client
         System.out.println( "\nsending to A>>> " + message );
	}
	if(x == 'B'){
	outputB.println( "client A>>> " + message );
         outputB.flush(); // flush output to client
         System.out.println( "\n sendint to B>>> " + message );
	}
     

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
