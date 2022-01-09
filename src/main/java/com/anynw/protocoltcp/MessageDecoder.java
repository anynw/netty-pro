package com.anynw.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author wuhp
 * @date 2022/1/9
 */
public class MessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MessageDecoder decode is called");
        //将得到的二进制字节码 读到 content中
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);
        //封装成messageProtocol 对象，放入 out 传递给下一个handler业务处理
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(len);
        messageProtocol.setContent(content);

        out.add(messageProtocol);
    }
}
