/*
 * This java application is a multithreaded TCP server that listens to a specific port
 * It has the ability to handle multiple client requests.
 * It is depended upon another class Executor to run the linux / unix commands.
 * 
*/
package agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class MultiThreadServer implements Runnable {
   Socket csocket;
   public static int noOfConnections = 0;
   
   /*Logging object declaration*/
   
   private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
   
   
   final static int socket = 5678;
   MultiThreadServer(Socket csocket) {
      this.csocket = csocket;
   }

   public static void main(String args[])throws Exception {
      ServerSocket ssock = new ServerSocket(socket);
      /*System.out.println("Listening");*/
      LOGGER.info("Server Listening to port: 5678");
      while (true) {
         Socket sock = ssock.accept();
         //System.out.println("Connected");
         LOGGER.info("New tcp connection established");
         noOfConnections++;
         new Thread(new MultiThreadServer(sock)).start();
      }
     
   }
   public void run() {
      try {
    	  // InputStream reader can read only characters
    	  // to read input line Buffered reader is needed
    	  String line;
    	  // for reading from input stream
    	  BufferedReader clientInputReader = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
    	  
    	  // for writing to output stream
    	  PrintStream pstream = new PrintStream(csocket.getOutputStream());
    	  
    	  ArrayList<Integer> fibo;
    	  while((line = clientInputReader.readLine()) != null) { //receives the n value of the fibo series from client 
    		  fibo = new ArrayList<>();
    		 // writing to the output stream of the socket to which client is connected
    		 for(int i=Integer.parseInt(line);  i >= 0;i-- )
    			 fibo.add(fibonacciGenerator(i));
    		 pstream.println(fibo);
    		 pstream.flush();
    		 
    	  }
         csocket.close();
         LOGGER.info("Connection Closed!");
      }
      catch (IOException e) {
         /*System.out.println(e);*/
    	  LOGGER.severe("IO interruption detected!");
      }
   }
   
   //returns the last fibonacci number in the series using recursion
   // if n = 5 returns  (0 1 1 2 3 5)
   public int fibonacciGenerator(int n){
	   
	   if(n == 0 || n == 1)
		   return 1;
	   
	   return fibonacciGenerator(n-1) + fibonacciGenerator(n-2); // recursively generates the fibo
   }
   
}