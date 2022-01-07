package com.anynw.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通道写数据
 * @author wuhp
 * @date 2022/1/6
 */
public class NIOFileChannelWrite {
    public static void main(String[] args) throws Exception {
        String str = "hello world";
        FileOutputStream fs = new FileOutputStream("/Users/wuhp/Documents/codes/NettyPro/file.txt");
        FileChannel channel = fs.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 将str写入
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        // 写入通道
        channel.write(byteBuffer);
        fs.close();
    }
}
