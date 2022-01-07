package com.anynw.nio;

import java.nio.ByteBuffer;

/**
 * @author wuhp
 * @date 2022/1/6
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }

        buffer.flip();
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }

        /**
         * public ByteBuffer put(byte x) {
         *      throw new ReadOnlyBufferException();
         * }
         */
        // readOnlyBuffer.put((byte)2);
    }
}
