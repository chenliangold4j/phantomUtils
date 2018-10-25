package liangNio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileUtil
{

//   FileChannel
//   DatagramChannel
//   SocketChannel
//   ServerSocketChannel
//   FileChannel 从文件中读写数据。
//
//   DatagramChannel 能通过UDP读写网络中的数据。
//
//   SocketChannel 能通过TCP读写网络中的数据。
//
//   ServerSocketChannel可以监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChannel。
   
   public static void readFileToByteBuffer(String path) throws IOException
   {

      RandomAccessFile aFile = new RandomAccessFile(path, "rw");
      FileChannel inChannel = aFile.getChannel();

      ByteBuffer buf = ByteBuffer.allocate(128);

      int bytesRead = inChannel.read(buf);
      while (bytesRead != -1)
      {
         System.out.println("Read " + bytesRead);
         buf.flip();
         while (buf.hasRemaining())
         {
            System.out.print((char) buf.get()); // read 1 byte at a time
         }
         buf.clear();
         bytesRead = inChannel.read(buf);
      }
      aFile.close();
   }
   
   public static void main(String[] args)
   {
      try
      {
         readFileToByteBuffer("G:/feat.txt");
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

}
