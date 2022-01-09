package com.anynw.codec2;

import com.anynw.codec.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * @author wuhp
 * @date 2022/1/8
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    /**
     * 通道就绪触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;
        if (random == 0) {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(1).setName("学生").build()).build();

        } else {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setAge(18).setName("打工人").build()).build();
        }
        ctx.writeAndFlush(myMessage);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        System.out.println("服务器回复的消息：" + msg.toString());
        System.out.println("服务器的地址：" + ctx.channel().remoteAddress());
    }
}
