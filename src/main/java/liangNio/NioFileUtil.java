package liangNio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

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
      
//      要想获得一个Buffer对象首先要进行分配。 每一个Buffer类都有一个allocate方法。下面是一个分配48字节capacity的ByteBuffer的例子。
//      ByteBuffer buf = ByteBuffer.allocate(48);
      
//      这是分配一个可存储1024个字符的CharBuffer：
//      CharBuffer buf = CharBuffer.allocate(1024);
      
//      capacity
//      作为一个内存块，Buffer有一个固定的大小值，也叫“capacity”.你只能往里写capacity个byte、long，char等类型。一旦Buffer满了，需要将其清空（通过读数据或者清除数据）才能继续写数据往里写数据。
//
//      position
//      当你写数据到Buffer中时，position表示当前的位置。初始的position值为0.当一个byte、long等数据写到Buffer后， 
//      position会向前移动到下一个可插入数据的Buffer单元。position最大可为capacity – 1.
//      当读取数据时，也是从某个特定位置读。当将Buffer从写模式切换到读模式，position会被重置为0.
//      当从Buffer的position处读取数据时，position向前移动到下一个可读的位置。
//
//      limit
//      在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。 写模式下，limit等于Buffer的capacity。
//
//      当切换Buffer到读模式时， limit表示你最多能读到多少数据。因此，当切换Buffer到读模式时，limit会被设置成写模式下的position值。
//      换句话说，你能读到之前写入的所有数据（limit被设置成已写数据的数量，这个值在写模式下就是position）
      ByteBuffer buf = ByteBuffer.allocate(128);

//      从Channel写到Buffer的例子
//      int bytesRead = inChannel.read(buf); //read into buffer.
      
//      通过put方法写Buffer的例子：
//      buf.put(127);
      
//    从Buffer读取数据到Channel的例子：
//    //read from buffer into channel.
//    int bytesWritten = inChannel.write(buf);
//    
//    使用get()方法从Buffer中读取数据的例子
//    byte aByte = buf.get();
      
//      rewind()方法
//      Buffer.rewind()将position设回0，所以你可以重读Buffer中的所有数据。limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。
//
//      clear()与compact()方法
//      一旦读完Buffer中的数据，需要让Buffer准备好再次被写入。可以通过clear()或compact()方法来完成。
//      更多方法参考别的资料
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
   
//   分散（scatter）从Channel中读取是指在读操作时将读取的数据写入多个buffer中。因此，Channel将从Channel中读取的数据“分散（scatter）”到多个Buffer中。
//   聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel，因此，Channel 将多个Buffer中的数据“聚集（gather）”后发送到Channel。
//
//   scatter / gather经常用于需要将传输的数据分开处理的场合，例如传输一个由消息头和消息体组成的消息，你可能会将消息体和消息头分散到不同的buffer中，这样你可以方便的处理消息头和消息体。
   public void test() {
      ByteBuffer header = ByteBuffer.allocate(128);
      ByteBuffer body   = ByteBuffer.allocate(1024);

      ByteBuffer[] bufferArray = { header, body };
//      Scattering Reads在移动下一个buffer前，必须填满当前的buffer，
//      这也意味着它不适用于动态消息(译者注：消息大小不固定)。
//      换句话说，如果存在消息头和消息体，消息头必须完成填充（例如 128byte），Scattering Reads才能正常工作。
//      channel.read(bufferArray);
//      
//      ByteBuffer header = ByteBuffer.allocate(128);
//      ByteBuffer body   = ByteBuffer.allocate(1024);
//
//      //write data into buffers
//
//      ByteBuffer[] bufferArray = { header, body };
//
//      channel.write(bufferArray);
   }
   
   
   public void cpFile() throws IOException {
      RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
      FileChannel      fromChannel = fromFile.getChannel();

      RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
      FileChannel      toChannel = toFile.getChannel();
      
      long position = 0;
      long count = fromChannel.size();

      toChannel.transferFrom((ReadableByteChannel) fromFile, position, count);
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
   
//   Selector一起使用时，Channel必须处于非阻塞模式下。这意味着不能将FileChannel与Selector一起使用，因为FileChannel不能切换到非阻塞模式。而套接字通道都可以。
   public void  selectortest(SocketChannel channel) throws IOException {
//      Selector的创建
//      通过调用Selector.open()方法创建一个Selector，
//      向Selector注册通道
//      为了将Channel和Selector配合使用，必须将channel注册到selector上。通过SelectableChannel.register()方法来实现，如下：
      
//     interest集合是你所选择的感兴趣的事件集合。可以通过SelectionKey读写interest集合，像这样：
//      int interestSet = selectionKey.interestOps();
//      boolean isInterestedInAccept  = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT；
//      boolean isInterestedInConnect = interestSet & SelectionKey.OP_CONNECT;
//      boolean isInterestedInRead    = interestSet & SelectionKey.OP_READ;
//      boolean isInterestedInWrite   = interestSet & SelectionKey.OP_WRITE;
//      可以看到，用“位与”操作interest 集合和给定的SelectionKey常量，可以确定某个确定的事件是否在interest 集合中。
//      register()方法会返回一个SelectionKey对象。这个对象包含了一些你感兴趣的属性：
//      interest集合
//      ready集合
//      Channel
//      Selector
      Selector selector = Selector.open();
      channel.configureBlocking(false);
      SelectionKey keys = channel.register(selector, SelectionKey.OP_READ);
      while(true) {
        int readyChannels = selector.select();
        if(readyChannels == 0) continue;
        Set selectedKeys = selector.selectedKeys();
        Iterator keyIterator = selectedKeys.iterator();
        while(keyIterator.hasNext()) {
          SelectionKey key = (SelectionKey) keyIterator.next();
          if(key.isAcceptable()) {
              // a connection was accepted by a ServerSocketChannel.
          } else if (key.isConnectable()) {
              // a connection was established with a remote server.
          } else if (key.isReadable()) {
              // a channel is ready for reading
          } else if (key.isWritable()) {
              // a channel is ready for writing
          }
          keyIterator.remove();
        }
      }
   }
   
   public void testPipe() throws IOException 
   {
      //    创建管道
      //    通过Pipe.open()方法打开管道。例如：
          Pipe pipe = Pipe.open();
      //    向管道写数据
      //    要向管道写数据，需要访问sink通道。像这样：
          Pipe.SinkChannel sinkChannel = pipe.sink();
      //     通过调用SinkChannel的write()方法，将数据写入SinkChannel,像这样：
          String newData = "New String to write to file..." + System.currentTimeMillis();
          ByteBuffer buf = ByteBuffer.allocate(48);
          buf.clear();
          buf.put(newData.getBytes());
          buf.flip();
          while(buf.hasRemaining()) {
              sinkChannel.write(buf);
          }
      //    从管道读取数据
      //    从读取管道的数据，需要访问source通道，像这样：
      
          Pipe.SourceChannel sourceChannel = pipe.source();
      //    调用source通道的read()方法来读取数据，像这样：
      
          ByteBuffer buf1 = ByteBuffer.allocate(48);
          int bytesRead = sourceChannel.read(buf1);
      //    read()方法返回的int值会告诉我们多少字节被读进了缓冲区。
 }
   
   

}
