package com.anynw.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wuhp
 * @date 2022/1/7
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 创建serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个selector对象
        Selector selector = Selector.open();
        //绑定端口6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //把serverSocketChannel注册到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端连接
        while (true) {
            if (selector.select(5000) == 0) {
                System.out.println("服务器等待了5秒钟，无连接事件");
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //通过selectionKeys反向获取到通道
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {//有客户端连接 OP_ACCEPT
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功,socketChannel = "+ socketChannel.hashCode());
                    //设置为非阻塞
                    socketChannel.configureBlocking(false);
                    // 将socketChannel 注册到selector 关注事件为OP_READ 同时给socketChannel 关联buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                    System.out.println("客户端连接后，注册的selectionKeys的数量 = " + selector.keys().size());
                }
                if (key.isReadable()) {//OP_READ
                    //通过key 反向获取对应的channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("从客户端" + new String(buffer.array()));
                }
                // 手动从集合中删除当前的selectionKey，防止重复操作
                keyIterator.remove();
            }
        }
    }
}
