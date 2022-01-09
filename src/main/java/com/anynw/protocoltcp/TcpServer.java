package com.anynw.protocoltcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * TCP粘包和拆包问题解决方案：
 * 1.使用自定义协议+编解码器来解决
 * 2.主要是解决服务器每次读取数据长度
 * @author wuhp
 * @date 2022/1/8
 */
public class TcpServer {
    public static void main(String[] args) throws InterruptedException {
        TcpServer nettyServer = new TcpServer();
        System.out.println("TcpServer is started");
        nettyServer.start(8888);
    }

    public void start(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TcpServerInitializer());

            ChannelFuture cf = bootstrap.bind(port).sync();

            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
