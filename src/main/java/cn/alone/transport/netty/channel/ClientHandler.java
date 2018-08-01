package cn.alone.transport.netty.channel;

import cn.alone.transport.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author RojerAlone
 * @Date 2018-08-01 20:54
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcResponse response = (RpcResponse) msg;
        ctx.writeAndFlush(response);
    }
}
