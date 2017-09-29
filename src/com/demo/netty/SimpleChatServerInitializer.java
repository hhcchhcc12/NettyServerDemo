package com.demo.netty;

/**
 * Created by Administrator on 2017/9/7.
 */
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;


public class SimpleChatServerInitializer extends
        ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(new IdleStateHandler(10, 0, 0));
//        pipeline.addLast("decoder", new StringDecoder());
//        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024,0,4,-4,0));
        pipeline.addLast("handler", new ServerHandler());

        System.out.println("SimpleChatClient:"+ch.remoteAddress() +"连接上");
    }
}