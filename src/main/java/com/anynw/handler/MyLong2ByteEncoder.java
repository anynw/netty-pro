package com.anynw.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wuhp
 * @date 2022/1/9
 */
public class MyLong2ByteEncoder extends MessageToByteEncoder<Long> {
    /**
     * 编码方法
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("Client:MyLong2ByteEncoder encode 方法被调用");
        System.out.println("msg = " + msg);
        out.writeLong(msg);
    }
}
