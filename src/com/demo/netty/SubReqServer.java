package com.demo.netty;

/**
 * Created by Administrator on 2017/9/7.
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;


public class SubReqServer {

    private static final int MAX_FRAME_LENGTH = 1024 * 1024;
    private static final int LENGTH_FIELD_LENGTH = 4;
    private static final int LENGTH_FIELD_OFFSET = 1;
    private static final int LENGTH_ADJUSTMENT = 0;
    private static final int INITIAL_BYTES_TO_STRIP = 0;

    public void run(int nPort) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);

        try{
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p = socketChannel.pipeline();
                            //p.addLast(new IdleStateHandler(10, 0, 0));
                            //p.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, -4, 0));
                            p.addLast(new IdleStateHandler(10, 0, 0));
                            p.addLast(new CustomEncoder());
                            p.addLast(new CustomDecoder(MAX_FRAME_LENGTH,LENGTH_FIELD_OFFSET,LENGTH_FIELD_LENGTH,
                                    LENGTH_ADJUSTMENT,INITIAL_BYTES_TO_STRIP,false));
                            p.addLast(new ServerHandler());
                        }
                    });

            System.out.println("服务器已启动，等待客户端连接");

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(nPort).sync(); // (7)

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();
        }finally {
            System.out.println("---------------Shut down");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args){
        int nPort = 9999;
        nPort = Integer.valueOf(nPort);
        System.out.println("---------------Main start");
        try {
            new SubReqServer().run(nPort);
        } catch (Exception e) {
            System.out.println("---------------Main Error");
            e.printStackTrace();
        }
    }
}
