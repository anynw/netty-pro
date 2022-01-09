package com.anynw.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author wuhp
 * @date 2022/1/9
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        //加入一个出栈的handler ，对数据进行编码
        p.addLast(new MyLong2ByteEncoder());
        //加入一个自定义的handler，处理业务逻辑
        p.addLast(new MyClientHandler());
    }
}
