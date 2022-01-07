package com.anynw.nio.zirocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author wuhp
 * @date 2022/1/8
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));
        String filename = "/Users/wuhp/Downloads/VMware_Fusion_Pro_12.2.1_(18811640)_(最低11.0)__macwk.com12.dmg";
        FileChannel fileChannel = new FileInputStream(filename).getChannel();
        long startTime = System.currentTimeMillis();
        // linux 系统下transferTo 方法可以完成传输
        // windows下 调用方法一次只能传输8M，需要分段传输
        // 零拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送总字节数：" + transferCount + "耗时：" + (System.currentTimeMillis() - startTime));

        fileChannel.close();
    }
}
