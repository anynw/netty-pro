package com.anynw.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wuhp
 * @date 2022/1/9
 */
public class MessageEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("TcpMessageEncoder encode is called");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
