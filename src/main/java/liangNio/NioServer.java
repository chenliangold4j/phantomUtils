package liangNio;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import javax.sound.sampled.Port;

public class NioServer 
{
	
	public static void main(String[] args) {
		int port = 8080;
//		Multiple
	}

}

class MultiplexerTimeServer implements Runnable{

	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	private volatile boolean stop;
	
	public MultiplexerTimeServer(int port) {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("the time server is start in port:"+port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void stop() {
		this.stop = true;
	}
	
	public void run() {
		while(!stop) {
			try {
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				SelectionKey key = null;
				while(iterator.hasNext()) {
					key = iterator.next();
					iterator.remove();
					try {
						handleInput(key);
					}catch (Exception e) {
						if(key != null) {
							key.cancel();
							if(key.channel()!=null) {
								key.channel().close();
							}
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(selector != null) {
			try {
				selector.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void handleInput(SelectionKey key)throws IOException {
		if(key.isValid()) {
			if(key.isAcceptable()) {
				ServerSocketChannel serverSocketChannel =  (ServerSocketChannel)key.channel();
				SocketChannel sChannel = serverSocketChannel.accept();
				sChannel.configureBlocking(false);
				sChannel.register(selector, SelectionKey.OP_READ);
			}
			
		}
		
	}
	
}
