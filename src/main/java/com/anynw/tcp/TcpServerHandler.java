package com.anynw.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @author wuhp
 * @date 2022/1/9
 */
public class TcpServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);

        //转字符串
        String message = new String(bytes, CharsetUtil.UTF_8);
        System.out.println("server recieve data is " + message + "\n");
        System.out.println();
        System.out.println("server revieve data count is " + (++this.count) + "\n");

        //服务器回送数据给客户端，随机id
        ByteBuf responseBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString() +" ", CharsetUtil.UTF_8);
        ctx.writeAndFlush(responseBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
