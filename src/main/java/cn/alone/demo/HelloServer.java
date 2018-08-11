package cn.alone.demo;

import cn.alone.transport.netty.AloneServer;

/**
 * @author RojerAlone
 * @Date 2018-08-11 17:34
 */
public class HelloServer {
    public static void main(String[] args) {
        AloneServer.start(HelloService.class, new HelloServiceImpl());
    }
}
