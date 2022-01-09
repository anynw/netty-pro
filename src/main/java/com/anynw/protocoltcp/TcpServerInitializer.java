package com.anynw.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author wuhp
 * @date 2022/1/9
 */
public class TcpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("TcpServerInitializer");
        ChannelPipeline pipeline = ch.pipeline();
        //加入解码器
        pipeline.addLast(new MessageDecoder());
        pipeline.addLast(new TcpServerHandler());
    }
}
