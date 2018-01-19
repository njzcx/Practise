package com.demo.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * WebSocket服务器
 * 实测在eclipse的tomcat7上部署，客户端连接不上
 * 导出war包，部署在tomcat8上，使用jdk1.7，一切正常。
 * @author zhangchx
 *
 */
//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Spring的注解。容器会为每一个连接创建一个EndPoint的实例。
@ServerEndpoint("/websocket")
public class WebSocketJavax {
	
	/*静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
	private static int onlineCount = 0;
	
	/*concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识*/
	private static CopyOnWriteArraySet<WebSocketJavax> websocketSet = new CopyOnWriteArraySet<WebSocketJavax>();
	
	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	 /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		websocketSet.add(this);
		addOnlineCount();
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}
	
	/**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
	@OnMessage(maxMessageSize=16*1024)
	public void onMessage(String message, Session session) {
		System.out.println("来自客户端的消息:" + message);
        //群发消息
        for(WebSocketJavax item: websocketSet){             
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
	}
	
	/**
     * 连接关闭调用的方法
     */
	@OnClose
	public void onClose() {
		websocketSet.remove(this);
		subOnlineCount();
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
     * 发生错误时调用
     * @param session
     * @param error
     */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
        error.printStackTrace();
	}
	/**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
    
	public static synchronized int getOnlineCount() {
		return onlineCount;
	}
	
	public static synchronized void addOnlineCount() {
		onlineCount ++;
	}
	
	public static synchronized void subOnlineCount() {
		onlineCount --;
	}
}
