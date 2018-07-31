package cn.alone.transport.netty.channel;

import cn.alone.transport.model.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by rojeralone on 2018-07-31
 */
public class InvokeHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // invoke method depend on registry center and request info
        RpcRequest rpcRequest = (RpcRequest) msg;
        if (rpcRequest.isAsync()) {
            // do async invoke
        } else {
            // do sync invoke
        }
        super.channelRead(ctx, msg);
    }
}
