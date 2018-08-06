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

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
        super.channelRegistered(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcResponse response = (RpcResponse) msg;
        ctx.writeAndFlush(response);
    }

    public void call(RpcRequest request) {
        channel.writeAndFlush(request);
    }
}
