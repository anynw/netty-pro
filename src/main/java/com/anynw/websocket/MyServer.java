package com.anynw.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author wuhp
 * @date 2022/1/9
 */
public class MyServer {

    public static void main(String[] args) throws InterruptedException {
        MyServer myServer = new MyServer();
        myServer.start(7000);
    }

    public void start(int port) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            //基于http协议，使用http的编码解码器
                            p.addLast(new HttpServerCodec());
                            //以块方式写
                            p.addLast(new ChunkedWriteHandler());
                            //因为http数据在传输过程中是分段的，HttpObjectAggregator可以将多个段聚合
                            p.addLast(new HttpObjectAggregator(8192));
                            //对应websocket，数据是以帧（frame）传送
                            //浏览器请求时 ws://localhost:prot/hello 表示请求的uri
                            //WebSocketServerProtocolHandler 核心功能是将http协议升级为ws协议，保持长连接
                            p.addLast(new WebSocketServerProtocolHandler("/hello"));
                            //自定义handler，处理业务逻辑
                            p.addLast(new MyTextWebSocketFrameHandler());
                        }
                    });

            ChannelFuture cf = b.bind(port).sync();
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
