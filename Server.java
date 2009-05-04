
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
   private Socket connectionB; // connection to client B

   private PrintWriter outToA;
   private InputStream inFromA; // input stream from client

   private PrintWriter outToB; // output stream to client
   private InputStream inFromB; // input stream from client

	private int port;
	private boolean A_PRESENT=false;
	private boolean B_PRESENT=false;





   private void Print (String s) { System.out.println(s); }


   public Server(int p)
   {
	port = p;

   } 

   // set up and run server 
   public void run()
   {
      try // set up server to receive connections; process connections
      {
         server = new ServerSocket( port, 100 ); // create ServerSocket

         while ( true ) 
         {
            try 
            {
               waitForConnections(); 
               getStreams(); // get input & output streams
               processConnections(); 
            } // end try
            catch ( EOFException eofException ) 
            {
               Print( "\nServer terminated connection" );
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
   } // end method run

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

	while(true) // process messages sent from client
	{ 
		tempMessage = new StringBuilder(); 
		while ( inFromA.available() >0 )tempMessage.append((char)inFromA.read());
		if (tempMessage.length()>0 ) outToB.println(tempMessage.toString());

		tempMessage = new StringBuilder(); 
		while ( inFromB.available() >0 ) tempMessage.append((char)inFromB.read());
		if (tempMessage.length()>0 ) outToA.println(tempMessage.toString());
	}
  }

	public void sendToPlayerA(String s) { outToA.println(s); }

	public void sendToPlayerB(String s) { outToB.println(s); }


   // close streams and socket
   private void closeConnection() 
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

