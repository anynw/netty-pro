package com.anynw.dubborpc.provider;

import com.anynw.dubborpc.netty.NettyServer;

/**
 * @author wuhp
 * @date 2022/1/10
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 7000);
    }
}
