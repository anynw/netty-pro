package com.anynw.dubborpc.service.impl;

import com.anynw.dubborpc.service.HelloService;

/**
 * @author wuhp
 * @date 2022/1/10
 */
public class HelloServiceImpl implements HelloService {
    private int count = 0;

    @Override
    public String hello(String msg) {
        System.out.println("HelloServiceImpl hello method is called ");
        return "hello client,I have recieve your message : " + msg + "第" + (++count);
    }
}
