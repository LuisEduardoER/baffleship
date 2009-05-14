
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
import java.util.*;


public class Server
{
	private ServerSocket server; // server socket

	//first one is player A, second one is player B, the rest are observers
	private java.util.List<Socket> clients = new ArrayList<Socket>();	
	private java.util.List<PrintWriter> outToClient = new ArrayList<PrintWriter>();
	private java.util.List<InputStream> inFromClient = new ArrayList<InputStream>();
	public java.util.List<String> names = new ArrayList<String>();	

	ServerGame theGame = new ServerGame(this);
	
	//private boolean 
	
	//debugging
   private void Print (String s) { System.out.println(s); }

   public int numPlayersConnected() {return clients.size();}
   
   public static void main( String args[] )
   {
	int port = 44771; //default port
	try { port= Integer.parseInt(args[0]); } catch (Exception e) {;}

	new Server(port); // create  and run server
   } 


   public Server(int port)
   {
      try // set up server to receive connections; process connections
      {
         server = new ServerSocket(port); // create ServerSocket
		 server.setSoTimeout(20); //now accept() throws SocketTimeoutException after 20ms

		while(true)
		{
			getConnection();
			processConnections();		
		}
      } // end try
      catch ( Exception e ) 
      {
         e.printStackTrace();
      } // end catch
   } // end constructor

   // wait a few ms for connection to arrive
   private void getConnection()
   {
	try {
		Socket temp = server.accept();
		clients.add(temp);
		outToClient.add(new PrintWriter(temp.getOutputStream(), true));
		inFromClient.add(temp.getInputStream());
		names.add(temp.getInetAddress().toString());
		Print("Connected to "+temp.getInetAddress());
		} catch ( SocketTimeoutException e) {;}
		  catch ( Exception e ) { e.printStackTrace(); }
      
   } 


   //check each connected client once for data, send it to the game, then return
   private void processConnections() throws IOException
   {
	StringBuilder tempMessage;
	
	for(int i=0;i<numPlayersConnected();i++)
	{
		tempMessage = new StringBuilder(); 
		while ( inFromClient.get(i).available() >0 )tempMessage.append((char)inFromClient.get(i).read());
		if (tempMessage.length()>0 ) theGame.inputFromPlayer(i,tempMessage.toString());
	}
	
   }

	public void sendToPlayer(int player, String s)
	{
		if ( (player >= numPlayersConnected() ) || (player<0) ) { Print("Cannot send to nonexistent player #"+player); return; } 
		
		System.out.println("Sending to player "+player+": "+s);
		outToClient.get(player).println(s);
	}
	
	public void sendToAllPlayers(String s)
	{
		System.out.println("Sending to all players: "+s);
		for(int i=0;i<numPlayersConnected();i++) outToClient.get(i).println(s);
	}	
	
	public void sendToAllPlayersExcept(int player, String s)
	{
		System.out.println("Sending to all players except "+player+": "+s);
		for(int i=0;i<numPlayersConnected();i++) if (i!=player) outToClient.get(i).println(s);
	}	


   // close streams and socket
   private void closeConnections() 
   {
      System.out.println( "\nTerminating connection\n" );
      try 
      {
		for(int i=0;i<numPlayersConnected();i++)
		{
			clients.get(i).close();
			outToClient.get(i).close();
			inFromClient.get(i).close();
		}
      } // end try
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } // end catch
   } // end method closeConnection


} // end class Server

