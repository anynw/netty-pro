package com.anynw.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通道读数据
 * @author wuhp
 * @date 2022/1/6
 */
public class NIOFileChannelRead {
    public static void main(String[] args) throws Exception {
        File file = new File("/Users/wuhp/Documents/codes/NettyPro/file.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        // 读取
        channel.read(byteBuffer);
        // 输出
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}
