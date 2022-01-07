package com.anynw.bio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO网络模型
 *
 * @author wuhp
 * @date 2022/1/6
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {
        // 线程池机制
        // 1.创建一个线程池
        // 2.如果有客户端连接，就创建一个线程与之通讯
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务端启动了");
        while (true) {
            // 模拟阻塞
            System.out.println("等待连接");
            // 监听，等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    public static void handler(Socket socket) {
        try {
            System.out.println("线程ID：" + Thread.currentThread().getId() + "名称：" + Thread.currentThread().getName());
            // 通过socket获取输入流
            byte[] bytes = new byte[2014];
            InputStream inputStream = socket.getInputStream();
            // 循环读取客户端发送的数据
            while (true) {
                // 模拟阻塞
                System.out.println("read......");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    // 输出客户端发送的数据
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
