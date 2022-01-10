package com.anynw.dubborpc.customer;

import com.anynw.dubborpc.constant.CommonContant;
import com.anynw.dubborpc.netty.NettyClient;
import com.anynw.dubborpc.service.HelloService;

/**
 * @author wuhp
 * @date 2022/1/10
 */
public class ClientBootstrap {

    public static void main(String[] args) {
        //创建消费者
        NettyClient customer = new NettyClient();
        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, CommonContant.PROVIDER_NAME);
        //通过代理对象调用服务提供者的方法
        // String res = service.hello("hello dubbo ~");

        for(;;){
            try {
                Thread.sleep(3*1000);
                String res = service.hello("hello dubbo ~");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
