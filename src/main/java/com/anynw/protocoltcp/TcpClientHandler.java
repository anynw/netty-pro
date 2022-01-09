package com.anynw.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author wuhp
 * @date 2022/1/9
 */
public class TcpClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("TcpClientHandler channelActive is called");
        for (int i = 0; i < 5; i++) {
            String msg = "this is my message";
            byte[] content = msg.getBytes(CharsetUtil.UTF_8);
            //创建协议包对象
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setContent(content);
            messageProtocol.setLen(content.length);

            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("TcpClientHandler channelRead0 is called");

        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("client recieve data is content = " + new String(content, CharsetUtil.UTF_8) + ";len = " + len);
        System.out.println("client recieve data count is " + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
