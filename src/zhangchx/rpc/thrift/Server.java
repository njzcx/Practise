package zhangchx.rpc.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class Server {
	/**
	 * 启动 Thrift 服务器
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// 指定ServerSocket，设置服务端口为 7911
			TServerSocket serverTransport = new TServerSocket(7911);
			
			TThreadPoolServer.Args params = new TThreadPoolServer.Args(
					serverTransport);
			// 关联处理器与 Hello 服务的实现
			params.processor(new Hello.Processor(new HelloServiceImpl()));
			// 设置协议工厂为 TBinaryProtocol.Factory
			params.protocolFactory(new TBinaryProtocol.Factory());

			TServer server = new TThreadPoolServer(params);
			System.out.println("Start server on port 7911...");
			server.serve();
		} catch (TTransportException e) {
			e.printStackTrace();
		}
	}
}
