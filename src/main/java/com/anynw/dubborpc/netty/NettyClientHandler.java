package com.anynw.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author wuhp
 * @date 2022/1/10
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String result;
    private String param;

    /**
     * 与服务器连接时，就会被调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("NettyClientHandler channelActive is called ");
        context = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("NettyClientHandler channelRead is called ");
        result = msg != null ? msg.toString() : null;
        notify();//唤醒等待的线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 被代理对象调用，发送数据给服务器 wait 等待 channelRead 被唤醒，返回结果
     *
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("NettyClientHandler call is called 1");
        context.writeAndFlush(param);
        wait();
        System.out.println("NettyClientHandler call is called 2");
        return result;
    }

    void setParam(String param) {
        System.out.println("setParam");
        this.param = param;
    }
}
