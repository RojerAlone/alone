package cn.alone.transport.netty;

import cn.alone.transport.netty.channel.ClientHandler;
import cn.alone.transport.netty.codec.AloneDecoder;
import cn.alone.transport.netty.codec.AloneEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
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

    private static final int DEFAULT_CLINET_PORT = 8864;

    static final ConcurrentLinkedQueue<ClientHandler> channelHandlers = new ConcurrentLinkedQueue<ClientHandler>();

    public void init() {
        try {
            startClient();
        } catch (InterruptedException e) {
            LOGGER.error("init failed", e);
            System.exit(-1);
        }
    }

    private void startClient() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("responseDecoder", new AloneDecoder(false));
                        ch.pipeline().addLast("requestEncoder", new AloneEncoder(true));
                        ClientHandler clientHandler = new ClientHandler();
                        channelHandlers.add(clientHandler);
                        ch.pipeline().addLast("clientHandler", clientHandler);
                    }
                });
        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1", DEFAULT_CLINET_PORT).sync();
            future.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static ClientHandler getHandler() {
        ClientHandler handler;
        while ((handler = channelHandlers.poll()) == null) {

        }
        return handler;
    }

    public static void releaseHandler(ClientHandler handler) {
        channelHandlers.add(handler);
    }
}
