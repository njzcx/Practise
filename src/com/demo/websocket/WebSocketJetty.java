package com.demo.websocket;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * 使用jetty包实现websocket。未成功，客户端连接不上
 * tomcat一个连接需要一个线程处理，而jetty多个连接一个线程处理，当用户连接很多时，jetty更安全。
 * @author zhangchx
 *
 */
@WebSocket
public class WebSocketJetty extends WebSocketHandler {

	@OnWebSocketClose
	public void onClose() {
		System.out.println("关闭WS");
	}
	
	@OnWebSocketError
	public void onError(Throwable t) {
		System.out.println("异常" + t.getMessage());
	}
	
	@OnWebSocketMessage
	public void onMessage(String message) {
	}
	
	@OnWebSocketConnect
	public void onOpen(Session session) {
		System.out.println("打开WS");
	}
	
	@Override
	public void configure(WebSocketServletFactory factory) {
		factory.register(WebSocketJetty.class);
	}
	
	public static void main(String[] args) throws Exception {
	    Server server = new Server(2014);
	    server.setHandler(new WebSocketJetty());
	    server.setStopTimeout(0);
	    server.start();
	    server.join();
	}
}
