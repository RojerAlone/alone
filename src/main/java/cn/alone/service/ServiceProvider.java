package cn.alone.service;

import cn.alone.registry.RegistryCenter;
import cn.alone.transport.model.RpcRequest;
import cn.alone.transport.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by rojeralone on 2018-08-10
 */
public class ServiceProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProvider.class);

    private static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(16);

    public static void request(final ChannelHandlerContext ctx, final RpcRequest request) {
        executor.execute(new Runnable() {
            public void run() {
                Object service = RegistryCenter.getService(request);
                RpcResponse response = new RpcResponse();
                response.setRid(request.getRid());
                try {
                    Method invokeMethod = service.getClass().getMethod(request.getMethodName(), request.getParamTypes());
                    Object object = invokeMethod.invoke(service, request.getParams());
                    response.setSuccess(true);
                    response.setResponseResult(object);
                } catch (Exception e) {
                    LOGGER.error("provider invoke error", e);
                    response.setSuccess(false);
                    response.setException(e);
                }
                // TODO invoke by isAsync
                if (request.isAsync()) {
                    // do async invoke
                } else {
                    // do sync invoke
                }
                ctx.writeAndFlush(response);
            }
        });
    }

    public static void shutdown() {
        executor.shutdownNow();
    }

}
