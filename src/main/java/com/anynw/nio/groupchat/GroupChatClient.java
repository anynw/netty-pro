package com.anynw.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author wuhp
 * @date 2022/1/7
 */
public class GroupChatClient {

    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + " is ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //发送消息
    public void sendMsg(String msg) {
        msg = username + "说：" + msg;
        try {
            String encoding = System.getProperty("file.encoding");
            // System.out.println("系统编码:" + encoding);
            socketChannel.write((ByteBuffer.wrap(msg.getBytes())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMsg() {
        try {
            int readChannels = selector.select();
            if (readChannels > 0) {//有可用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        sc.read(byteBuffer);
                        String msg = new String(byteBuffer.array());
                        System.out.println(msg.trim());
                    }
                }
                //  移除当前key
                iterator.remove();
            } else {
                // System.out.println("没有可用的通道......");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        //启动客户端
        GroupChatClient client = new GroupChatClient();
        //启动一个线程 每隔3秒， 读取从服务器发动数据
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    client.readMsg();
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //发动数据给服务器
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            client.sendMsg(scanner.nextLine());
        }

    }
}
