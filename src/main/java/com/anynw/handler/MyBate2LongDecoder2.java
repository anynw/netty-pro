package com.anynw.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * ReplayingDecoder：
 * 使用方便，但是存在局限性
 * 1.并不是所有的ByteBuf操作都支持
 * 2.在某些情况下，可能稍慢于ByteToMessageDecoder，例如 网络缓慢并且消息格式复杂时，消息会被拆分成多个碎片，速度变慢
 *
 * @author wuhp
 * @date 2022/1/9
 */
public class MyBate2LongDecoder2 extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("Server：MyBate2LongDecoder2-ReplayingDecoder decode 方法被调用");
        //ReplayingDecoder 不需要判断数据是否足够读取，内部已经处理
        out.add(in.readLong());
    }
}
