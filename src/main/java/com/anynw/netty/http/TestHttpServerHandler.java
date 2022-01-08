package com.anynw.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;


/**
 * @author wuhp
 * @date 2022/1/8
 */
// @ChannelHandler.Sharable
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("TestHttpServerHandler。。。。。。");
        System.out.println(msg);
        //判断是否为http请求
        if (msg instanceof HttpRequest) {
            System.out.println("msg 类型:" + msg.getClass());
            System.out.println("客户端地址:" + ctx.channel().remoteAddress());

            //回信息给服务器
            ByteBuf content = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);
            DefaultFullHttpResponse response =
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            //返回response
            ctx.writeAndFlush(response);
        }
    }
}
