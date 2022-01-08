package com.anynw.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.NettyRuntime;

/**
 * @author wuhp
 * @date 2022/1/8
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(8888);
        // 获取CPU为几核
        System.out.println(NettyRuntime.availableProcessors());
    }

    public void start(int port) throws InterruptedException {
        //只处理请求，无限循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //工作线程组，处理客户端业务，无限循环
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            // 创建服务端的启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            //配置参数,链式编程
            bootstrap.group(bossGroup, workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器的通道实现

                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new NettyServerHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)//设置线程连接数量
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("服务器 启动了。。。。。。");
            //启动服务器，绑定端口
            ChannelFuture cf = bootstrap.bind(port).sync();
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
