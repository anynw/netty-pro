package com.anynw.nio;

import sun.awt.image.PixelConverter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author wuhp
 * @date 2022/1/7
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        // 得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        // 提供服务器端ip 端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",6666);
        // 连接服务器
        if(!socketChannel.connect(inetSocketAddress)){
            while(!socketChannel.finishConnect()){
                System.out.println("连接需要时间，客户端不会阻塞");
            }
        }
        // 连接成功
        String str = "hello world";
        //将字节数组包装到缓冲区中
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        // 发送数据
        socketChannel.write(buffer);
        System.in.read();


    }
}
