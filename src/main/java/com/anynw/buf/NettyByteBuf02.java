package com.anynw.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author wuhp
 * @date 2022/1/8
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        // Charset.forName("UTF-8")
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
        if (byteBuf.hasArray()) {
            byte[] array = byteBuf.array();
            System.out.println(new String(array, CharsetUtil.UTF_8));
            System.out.println(byteBuf);
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println("可读的字节数：" + byteBuf.readableBytes());
            byteBuf.readByte();
            System.out.println("读取了一次，可读的字节数：" + byteBuf.readableBytes());
            System.out.println(byteBuf.getCharSequence(0, 4, Charset.forName("UTF-8")));
            System.out.println(byteBuf.getCharSequence(4, 6, Charset.forName("UTF-8")));
        }
    }
}
