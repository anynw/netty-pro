package com.anynw.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author wuhp
 * @date 2022/1/9
 */
public class MyByte2LongDecoder extends ByteToMessageDecoder {
    /**
     * 1.根据接收的数据，被调用多次，直到确定么有新的元素被添加到list，或者bytebuf没有更多可读的字节为止
     * 2.如果list out 不为空，就会将list的内容传递给下一个channelInboundHandler处理，该处理器的方法也会被调用多次
     *
     * @param ctx 上下文
     * @param in  入栈的 ByteBuf
     * @param out List集合 将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("Server：MyByte2LongDecoder decode 方法被调用");
        //long 8个字节
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
