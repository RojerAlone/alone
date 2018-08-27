package cn.alone.netty;

import cn.alone.netty.handler.ServerHandler;
import cn.alone.registry.RegistryCenter;
import cn.alone.service.ServiceProvider;
import cn.alone.transport.codec.AloneDecoder;
import cn.alone.transport.codec.AloneEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by rojeralone on 2018-07-25
 */
public class AloneServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AloneServer.class);

    private static final int DEFAULT_SERVER_PORT = 19981;

    private static final AtomicBoolean SERVER_STAT = new AtomicBoolean(false);

    private static Channel channel;
    private static EventLoopGroup bossGroup;
    private static EventLoopGroup workGroup;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                close();
            }
        }));
    }

    public static void start(Class interfaceClass, Object serviceImpl) {
        start(interfaceClass, serviceImpl, DEFAULT_SERVER_PORT);
    }

    public static void start(Class interfaceClass, Object serviceImpl, int port) {
        start(port);
        RegistryCenter.registry(interfaceClass, serviceImpl);
    }

    private static synchronized void start(int port) {
        if (port <= 1024) {
            LOGGER.error("invalid server port");
            System.exit(-1);
        }
        bossGroup = new NioEventLoopGroup();
        workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("requestDecoder", new AloneDecoder(true));
                            ch.pipeline().addLast("responseEncoder", new AloneEncoder());
                            ch.pipeline().addLast("serverHandler", new ServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            channel = future.channel();
            SERVER_STAT.set(true);
            System.out.println("server start successfully at port " + port);
        } catch (Exception e) {
            LOGGER.error("server start failed", e);
            System.exit(-1);
        }
    }

    public static synchronized void close() {
        if (SERVER_STAT.get()) {
            channel.close();
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            ServiceProvider.shutdown();
        }
    }

}
