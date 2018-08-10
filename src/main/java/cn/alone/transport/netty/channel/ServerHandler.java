package cn.alone.transport.netty.channel;

import cn.alone.service.ServiceProvider;
import cn.alone.transport.model.RpcRequest;
import cn.alone.transport.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by rojeralone on 2018-08-10
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest request = (RpcRequest) msg;
        RpcResponse response = ServiceProvider.request(request);
        ctx.writeAndFlush(response);
    }
}
