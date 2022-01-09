package com.anynw.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.security.acl.LastOwnerException;

/**
 * @author wuhp
 * @date 2022/1/9
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("Client:MyClientHandler channelRead0");
    }

    /**
     * 发送数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client:MyClientHandler 发送数据");
        ctx.writeAndFlush(123456L);
        //ctx.writeAndFlush(Unpooled.copiedBuffer("qwertyui98655kkkkjjasdf", CharsetUtil.UTF_8));
    }
}
