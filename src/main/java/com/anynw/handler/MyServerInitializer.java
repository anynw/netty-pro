package com.anynw.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author wuhp
 * @date 2022/1/9
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        //加入解码器
        // p.addLast(new MyByte2LongDecoder());

        p.addLast(new MyBate2LongDecoder2());

        p.addLast(new MyLong2ByteEncoder());

        //加入自定义handler
        p.addLast(new MyServerHandler());

        // p.addLast(new MyByte2LongDecoder());//MyServerHandler不被执行
    }
}
