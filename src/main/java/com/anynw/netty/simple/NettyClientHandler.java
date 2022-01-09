package com.anynw.netty.simple;

import com.anynw.codec.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author wuhp
 * @date 2022/1/8
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道就绪触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // System.out.println("client ctx = " + ctx);
        // ctx.writeAndFlush(Unpooled.copiedBuffer("hello,server:哈哈哈😄", CharsetUtil.UTF_8));

        // 需要加入编码器 encoder
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("张三").build();
        ctx.writeAndFlush(student);

    }

    /**
     * 当通道有读取事物时触发该方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复的消息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址：" + ctx.channel().remoteAddress());
        //释放资源
        buf.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
