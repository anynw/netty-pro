package com.anynw.nio;

import java.nio.ByteBuffer;

/**
 * @author wuhp
 * @date 2022/1/6
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        //类型化方法放入数据
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('A');
        buffer.flip();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
    }
}
