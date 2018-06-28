package zhangchx.io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class IOClient {
	
	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("127.0.0.1", 8888);
		OutputStream out = socket.getOutputStream();
		out.write("你好".getBytes());
		out.flush();
	}
}
