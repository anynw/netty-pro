package com.anynw.codec2;

import com.anynw.codec.StudentPOJO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.util.NettyRuntime;

/**
 * @author wuhp
 * @date 2022/1/8
 */
public class NettyServer {


    public static void main(String[] args) throws InterruptedException {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(9999);
        // 获取CPU为几核
        System.out.println(NettyRuntime.availableProcessors());
    }

    public void start(int port) throws InterruptedException {
        //只处理请求，无限循环，默认1
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //工作线程组，处理客户端业务，无限循环，默认16
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
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
                            ChannelPipeline p = ch.pipeline();
                            p.addLast("decoder",new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
                            p.addLast(new NettyServerHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)//设置线程连接数量
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //启动服务器，绑定端口
            ChannelFuture cf = bootstrap.bind(port).sync();
            System.out.println("服务器 启动了。。。。。。");

            //注册监听器，监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()) {
                        System.out.println("监听端口" + port + "成功");
                    } else {
                        System.out.println("监听端口" + port + "失败");
                    }
                }
            });

            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
