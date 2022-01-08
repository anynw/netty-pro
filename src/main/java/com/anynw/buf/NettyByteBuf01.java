package com.anynw.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;

/**
 * @author wuhp
 * @date 2022/1/8
 */
public class NettyByteBuf01 {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);
        //底层维护一个readerIndex和writerIndex 不需要 反转 flip
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        System.out.println("capacity = " + buffer.capacity());
        for (int i = 0; i < buffer.capacity(); i++) {
            // System.out.print(buffer.getByte(i));
            System.out.print(buffer.readByte());
        }

    }
}
