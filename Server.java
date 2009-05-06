
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
	private ServerSocket server; // server socket

	private Socket connectionA; // connection to client A
	private PrintWriter outToA; // output stream to client
	private InputStream inFromA; // input stream from client

	private Socket connectionB; // connection to client B
	private PrintWriter outToB; // output stream to client
   	private InputStream inFromB; // input stream from client

	ServerGame theGame = new ServerGame(this);

   private void Print (String s) { System.out.println(s); }

   public static void main( String args[] )
   {
	int port = 44771; //default port
	try { port= Integer.parseInt(args[0]); } catch (Exception e) {;}

      	Server application = new Server(port); // create  and run server
   } 


   public Server(int port)
   {

      try // set up server to receive connections; process connections
      {
         server = new ServerSocket(port); // create ServerSocket

            try 
            {
               waitForConnections(); 
               getStreams(); // get input & output streams
               processConnections(); //loops until a connection is closed
            } 
            catch ( EOFException eofException ) 
            {
               Print( "\nServer terminated connection" );
            } 
            finally 
            {
               closeConnections();          
            } 

      } // end try
      catch ( Exception e ) 
      {
         e.printStackTrace();
	 Print("Can't listen on port "+port);
      } // end catch
   } // end constructor

   // wait for connection to arrive, then display connection info
   private void waitForConnections() throws IOException
   {
	Print( "Waiting for connection\n" );

	connectionA = server.accept(); // allow server to accept connection  
	Print( "Connection received from: " + connectionA.getInetAddress().getHostName() );

	connectionB = server.accept();        
	Print( "Connection received from: " + connectionB.getInetAddress().getHostName() );
   } 

   // get streams to send and receive data
   private void getStreams() throws IOException
   {    
     	outToA = new PrintWriter(connectionA.getOutputStream(), true);
	inFromA = connectionA.getInputStream();

	outToB = new PrintWriter(connectionB.getOutputStream(), true);
	inFromB = connectionB.getInputStream();
   }

   // process connection with client
   private void processConnections() throws IOException
   {
	StringBuilder tempMessage;

	while( (connectionA.isConnected() ) && (connectionB.isConnected() ) ) // process messages sent from client
	{ 
		tempMessage = new StringBuilder(); 
		while ( inFromA.available() >0 )tempMessage.append((char)inFromA.read());
		if (tempMessage.length()>0 ) theGame.inputFromPlayer('A',tempMessage.toString());

		tempMessage = new StringBuilder(); 
		while ( inFromB.available() >0 ) tempMessage.append((char)inFromB.read());
		if (tempMessage.length()>0 ) theGame.inputFromPlayer('B',tempMessage.toString());
	}
  }

	public void sendToPlayer(char player, String s)
	{
		System.out.println("Sending to player "+player+": "+s);
		if (player == 'A') outToA.println(s);
		if (player == 'B') outToB.println(s);
	}


   // close streams and socket
   private void closeConnections() 
   {
      System.out.println( "\nTerminating connection\n" );
      try 
      {
         inFromA.close(); // close output stream
         outToA.close(); // close input stream
         connectionA.close(); // close socket
	 inFromB.close(); // close output stream
         outToB.close(); // close input stream
         connectionB.close(); // close socket
      } // end try
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } // end catch
   } // end method closeConnection



} // end class Server

