package com.anynw.nio.zirocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 传统java io 客户端
 *
 * @author wuhp
 * @date 2022/1/8
 */
public class OldIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 7001);
        String filename = "/Users/wuhp/Downloads/VMware_Fusion_Pro_12.2.1_(18811640)_(最低11.0)__macwk.com12.dmg";
        FileInputStream fileInputStream = new FileInputStream(filename);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] bytes = new byte[4096];
        long readCount;
        long total = 0;
        long startTime = System.currentTimeMillis();
        while ((readCount = fileInputStream.read(bytes)) > 0) {
            total += readCount;
            dataOutputStream.write(bytes);
        }
        System.out.println("发送总字节数："+total+"耗时："+(System.currentTimeMillis()-startTime));
    }
}
