package cn.alone.netty.handler;

import cn.alone.model.RpcRequest;
import cn.alone.service.ServiceProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by rojeralone on 2018-08-10
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest request = (RpcRequest) msg;
        ServiceProvider.request(ctx, request);
    }
}
