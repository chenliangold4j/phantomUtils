package server.socket.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener
{

   public static void listenPort(int port) throws IOException
   {
      ServerSocket server = new ServerSocket(port);
      while (true)
      {
         Socket socket = server.accept();
         System.out.println("接收到连接");
         new ServerThread(socket).start();
      }

   }

   public static void main(String[] args) throws IOException
   {        
      listenPort(8080);
   }
}

class ServerThread extends Thread
{
   Socket socket;

   ServerThread(Socket socket)
   {
      this.socket = socket;
   }

   @Override
   public void run()
   {
      System.out.println("run ");
      try
      {
         BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         String str = reader.readLine();
         while (str != null)
         { 
            System.out.println(str);
            str = reader.readLine();
         }
         reader.close();
         socket.close();

      }
      catch (UnsupportedEncodingException e)
      {
         e.printStackTrace();
         
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }

   }

}
