package com.anynw.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author wuhp
 * @date 2022/1/8
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //处理http的编码解码器
        // pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        // pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new TestHttpServerHandler());
    }
}
