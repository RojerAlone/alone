package cn.alone.transport.netty.channel;

import cn.alone.transport.model.RpcFuture;
import cn.alone.transport.model.RpcRequest;
import cn.alone.transport.model.RpcResponse;
import cn.alone.transport.util.RequestHolder;
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

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcResponse response = (RpcResponse) msg;
        RpcFuture future = RequestHolder.get(response.getRid());
        if (future != null) {
            future.done(response);
            RequestHolder.remove(response.getRid());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("client handler error", cause);
    }

}
