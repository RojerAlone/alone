package cn.alone.demo;

import cn.alone.transport.netty.AloneServer;
import cn.alone.transport.proxy.ClientFactory;

/**
 * @author RojerAlone
 * @Date 2018-08-11 13:14
 */
public class HelloClient {

    public static void main(String[] args) {
        HelloService helloService = ClientFactory.getProxy(HelloService.class);
        System.out.println(helloService.sayHello());
    }

}
