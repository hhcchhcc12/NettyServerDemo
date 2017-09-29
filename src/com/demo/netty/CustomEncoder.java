package com.demo.netty;

/**
 * Created by Administrator on 2017/9/29.
 */
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CustomEncoder extends MessageToByteEncoder<TCPData> {

    @Override
    protected void encode(ChannelHandlerContext ctx, TCPData msg, ByteBuf out) throws Exception {
        if(null == msg){
            throw new Exception("msg is null");
        }

        String body = msg.getContent();
        byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));
        out.writeByte(msg.getMsgType());
        //out.writeByte(msg.getFlag());
        out.writeInt(bodyBytes.length);
        out.writeBytes(bodyBytes);

    }

}
