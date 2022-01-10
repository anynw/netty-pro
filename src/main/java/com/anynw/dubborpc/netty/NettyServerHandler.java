package com.anynw.dubborpc.netty;

import com.anynw.dubborpc.constant.CommonContant;
import com.anynw.dubborpc.service.impl.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wuhp
 * @date 2022/1/10
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // System.out.println("msg = " + msg);
        //客户端在调用服务器的api时，需要定义一个协议
        if (msg != null && msg.toString().startsWith(CommonContant.PROVIDER_NAME)) {
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
