package zhangchx.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServer {
	
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8888);
		Socket socket = server.accept();
		InputStream in = socket.getInputStream();
		byte[] b = new byte[1024];
		while(in.read(b) > 0) {
			System.out.println(new String(b));
		}
	}
}
