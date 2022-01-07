package com.anynw.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 可以直接在堆外内存修改，操作系统不需要拷贝一次
 *
 * @author wuhp
 * @date 2022/1/6
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception {
        // rw :读写
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        // 5 代表最多修改5个字节，最大下标为4
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'q');
        mappedByteBuffer.put(3, (byte) '9');

    }
}
