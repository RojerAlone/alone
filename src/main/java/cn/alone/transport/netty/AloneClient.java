package cn.alone.transport.netty;

import cn.alone.transport.netty.codec.AloneDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
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

    private static final int DEFAULT_CLINET_PORT = 8864;

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
                    }
                });
        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1", DEFAULT_CLINET_PORT).sync();
            future.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
