package zhangchx.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class Client {
	public static void main(String[] args) throws InterruptedException {  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        Bootstrap bootstrap = new Bootstrap();  
        bootstrap.group(workerGroup)  
                .channel(NioSocketChannel.class)  
                .handler(new ChannelInitializer<SocketChannel>() {  
                    @Override  
                    protected void initChannel(SocketChannel socketChannel) throws Exception {  
                        socketChannel.pipeline().addLast(new ClientHandler());  
                    }  
                });  
        ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();  
        future.channel().writeAndFlush(Unpooled.copiedBuffer("777".getBytes()));  
        future.channel().closeFuture().sync();  
        workerGroup.shutdownGracefully();  
    }  
}

class ClientHandler extends ChannelInboundHandlerAdapter {  
	  
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
        try {  
            ByteBuf buf = (ByteBuf) msg;  
            byte[] data = new byte[buf.readableBytes()];  
            buf.readBytes(data);  
            System.out.println("Client：" + new String(data).trim());  
        } finally {  
            ReferenceCountUtil.release(msg);  
        }  
    }  
  
  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
        cause.printStackTrace();  
        ctx.close();  
    }  
  
}  
