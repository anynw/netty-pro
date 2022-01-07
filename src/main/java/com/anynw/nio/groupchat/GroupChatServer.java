package com.anynw.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author wuhp
 * @date 2022/1/7
 */
public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                // int count = selector.select(2000);// 非阻塞
                int count = selector.select();// 阻塞
                if (count > 0) {
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        if (key.isAcceptable()) {//连接
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);//注册
                            System.out.println(sc.getRemoteAddress() + "上线");
                        }
                        if (key.isReadable()) {//可读状态
                            readData(key);
                        }
                        // 删除当前key，防止重复处理
                        keyIterator.remove();
                    }
                } else {
                    System.out.println("等待......");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取客户端消息
    public void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = channel.read(byteBuffer);
            if (count > 0) {//读取到了数据
                String msg = new String(byteBuffer.array());
                System.out.println("客户端：" + msg);//输出消息
                // 转发消息
                sendMsg2OtherChients(msg, channel);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // try {
            //System.out.println(channel.getRemoteAddress() + "离线了......");
            // 取消注册，关闭通道
            //key.cancel();
            //channel.close();
            // } catch (IOException ioException) {
            //     ioException.printStackTrace();
            // }
        }
    }

    // 转发消息给其他客户端（通道）
    private void sendMsg2OtherChients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中......");
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        // 启动服务器端
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

}
