package com.anynw.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author wuhp
 * @date 2022/1/9
 */
public class TcpClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("TcpClientInitializer initChannel is called");
        ChannelPipeline p = ch.pipeline();
        //加入编码器
        p.addLast(new MessageEncoder());
        p.addLast(new TcpClientHandler());
    }
}
