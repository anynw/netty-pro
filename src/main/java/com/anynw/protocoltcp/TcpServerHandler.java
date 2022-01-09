package com.anynw.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author wuhp
 * @date 2022/1/9
 */
public class TcpServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //接收到信息，并进行处理
        byte[] content = msg.getContent();
        int len = msg.getLen();
        System.out.println("server recieve data is content = " + new String(content, CharsetUtil.UTF_8) + ";len = " + len);
        System.out.println("server recieve data count is " + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
