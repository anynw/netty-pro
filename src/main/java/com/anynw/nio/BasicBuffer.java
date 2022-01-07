package com.anynw.nio;

import java.nio.IntBuffer;

/**
 * @author wuhp
 * @date 2022/1/6
 */
public class BasicBuffer {
    public static void main(String[] args) {
        // 创建buffer，大小为5，可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        // 读写切换
        intBuffer.flip();
        intBuffer.position(1);
        intBuffer.limit(3);
        // 读数据
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

    }
}
