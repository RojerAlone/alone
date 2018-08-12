package cn.alone.transport.netty;

import cn.alone.transport.model.RpcFuture;
import cn.alone.transport.model.RpcRequest;
import cn.alone.transport.model.RpcResponse;
import cn.alone.transport.netty.channel.ClientHandler;
import cn.alone.transport.netty.codec.AloneDecoder;
import cn.alone.transport.netty.codec.AloneEncoder;
import cn.alone.transport.util.RequestHolder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author RojerAlone
 * @Date 2018-08-01 20:48
 */
public class AloneClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AloneClient.class);

    private static final int DEFAULT_SERVER_PORT = 19981;

    private static final Bootstrap bootstrap = new Bootstrap();
    private static EventLoopGroup eventLoopGroup;
    private static Channel clientChannel;

    static {
        init();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                shutdown();
            }
        }));
    }

    private static void init() {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("responseDecoder", new AloneDecoder(false));
                        ch.pipeline().addLast("requestEncoder", new AloneEncoder(true));
                        ch.pipeline().addLast("clientHandler", new ClientHandler());
                    }
                });
        try {
            clientChannel = bootstrap.connect("127.0.0.1", DEFAULT_SERVER_PORT).sync().channel();
            System.out.println("client start successfully listening to port " + DEFAULT_SERVER_PORT);
        } catch (InterruptedException e) {
            LOGGER.error("client init failed", e);
        }
    }

    public static RpcResponse request(RpcRequest request) {
        RpcFuture future = new RpcFuture();
        RequestHolder.put(request.getRid(), future);
        clientChannel.writeAndFlush(request);
        try {
            return future.get();
        } catch (InterruptedException e) {
            LOGGER.error("client call error", e);
        }
        return null;
    }

    public static void shutdown() {
        if (clientChannel != null) {
            clientChannel.close();
        }
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }

}
