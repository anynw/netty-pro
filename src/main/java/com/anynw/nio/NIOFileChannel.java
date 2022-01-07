package com.anynw.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author wuhp
 * @date 2022/1/6
 */
public class NIOFileChannel {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel channel1 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel channel2 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true) {//循环读取
            // 清空buff
            byteBuffer.clear();
            int read = channel1.read(byteBuffer);
            if (read == -1) {
                break;
            }
            // 写入 2
            byteBuffer.flip();
            channel2.write(byteBuffer);
        }
        // 关闭流
        fileOutputStream.close();
        fileInputStream.close();

    }
}
