package cn.alone.transport.netty.channel;

import cn.alone.transport.model.RpcRequest;
import cn.alone.transport.model.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author RojerAlone
 * @Date 2018-08-01 20:54
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    private Channel channel;

    private RpcResponse response;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = (RpcResponse) msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("client handler error", cause);
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public RpcResponse call(RpcRequest request) {
        channel.writeAndFlush(request);
        RpcResponse rpcResponse;
        while ((rpcResponse = response) == null) {

        }
        response = null;
        return rpcResponse;
    }
}
