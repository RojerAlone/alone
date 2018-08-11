package cn.alone.transport.netty;

import cn.alone.transport.netty.channel.ClientHandler;
import cn.alone.transport.netty.codec.AloneDecoder;
import cn.alone.transport.netty.codec.AloneEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author RojerAlone
 * @Date 2018-08-01 20:48
 */
public class AloneClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AloneClient.class);

    private static final int DEFAULT_SERVER_PORT = 19981;

//    private static final ConcurrentLinkedQueue<ClientHandler> channelHandlers = new ConcurrentLinkedQueue<ClientHandler>();
    private static ClientHandler clientHandler;

    private static final AloneClient self = new AloneClient();

    private static Channel clientChannel;
    private static EventLoopGroup eventLoopGroup;

    static {
        self.init();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                if (clientChannel != null) {
                    clientChannel.close();
                }
                if (eventLoopGroup != null) {
                    eventLoopGroup.shutdownGracefully();
                }
            }
        }));
    }

    public void init() {
        try {
            startClient();
        } catch (InterruptedException e) {
            LOGGER.error("init failed", e);
            System.exit(-1);
        }
    }

    private void startClient() throws InterruptedException {
        eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        final ClientHandler tmpClientHandler = new ClientHandler();
//        channelHandlers.add(clientHandler);
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("responseDecoder", new AloneDecoder(false));
                        ch.pipeline().addLast("requestEncoder", new AloneEncoder(true));
                        ch.pipeline().addLast("clientHandler", tmpClientHandler);
                    }
                });
        ChannelFuture future = bootstrap.connect("127.0.0.1", DEFAULT_SERVER_PORT).sync();
        clientChannel = future.channel();
        tmpClientHandler.setChannel(clientChannel);
        clientHandler = tmpClientHandler;
        System.out.println("client start successfully listening to port " + DEFAULT_SERVER_PORT);
    }

    public static ClientHandler getHandler() {
        ClientHandler handler;
//        while ((handler = channelHandlers.poll()) == null) {
//
//        }
//        return handler;
        return clientHandler;
    }

//    public static void releaseHandler(ClientHandler handler) {
//        channelHandlers.add(handler);
//    }
}
