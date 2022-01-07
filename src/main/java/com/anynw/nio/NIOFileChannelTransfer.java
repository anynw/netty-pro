package com.anynw.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * 通道传输数据
 * @author wuhp
 * @date 2022/1/6
 */
public class NIOFileChannelTransfer {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("1.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("2.jpg");

        FileChannel channel1 = fileInputStream.getChannel();
        FileChannel channel2 = fileOutputStream.getChannel();

        channel2.transferFrom(channel1, 0, channel1.size());
        channel1.close();
        channel2.close();
        fileOutputStream.close();
        fileInputStream.close();
    }
}
