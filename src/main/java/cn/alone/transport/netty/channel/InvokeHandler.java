package cn.alone.transport.netty.channel;

import cn.alone.registry.RegistryCenter;
import cn.alone.transport.model.RpcRequest;
import cn.alone.transport.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by rojeralone on 2018-07-31
 */
public class InvokeHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest rpcRequest = (RpcRequest) msg;
        RpcResponse rpcResponse = new RpcResponse().setRid(rpcRequest.getRid());
        doInvoke(rpcRequest, rpcResponse);
        ctx.writeAndFlush(rpcResponse);
    }

    private void doInvoke(final RpcRequest request, final RpcResponse response) {
        // invoke method depend on registry center and request info
        Object service = RegistryCenter.getService(request);
        try {
            Method invokeMethod = service.getClass().getMethod(request.getMethodName(), request.getParamTypes());
            Object object = invokeMethod.invoke(service, request.getParams());
            response.setSuccess(true);
            response.setResponseResult(object);
        } catch (Exception e) {
            response.setSuccess(false);
            if (e instanceof NoSuchMethodException) {
                response.setException(e);
            }
        }
        // TODO invoke by isAsync
        if (request.isAsync()) {
            // do async invoke
        } else {
            // do sync invoke
        }
    }
}
