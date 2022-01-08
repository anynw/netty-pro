package com.anynw.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author wuhp
 * @date 2022/1/8
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        NettyClient nettyClient = new NettyClient();
        nettyClient.connect("127.0.0.1", 8888);
    }

    public void connect(String host, int port) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });

            System.out.println("客户端 ok.......");
            // 启动客户端，连接服务端
            // ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1",8888));
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            //关闭通道监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
