package com.anynw.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author wuhp
 * @date 2022/1/9
 */
public class MyClient {
    public static void main(String[] args) throws InterruptedException {
        MyClient myClient = new MyClient();
        myClient.connect("127.0.0.1", 7000);
    }

    public void connect(String host, int port) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());//自定义初始化类

            ChannelFuture cf = b.connect(host, port).sync();
            cf.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }


}
