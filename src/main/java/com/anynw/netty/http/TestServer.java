package com.anynw.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author wuhp
 * @date 2022/1/8
 */
public class TestServer {
    public static void main(String[] args) throws InterruptedException {
        TestServer testServer = new TestServer();
        testServer.start(6969);
    }

    public void start(int port) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestHttpServerHandler());

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("服务器端 启动了。。。。。。");
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
