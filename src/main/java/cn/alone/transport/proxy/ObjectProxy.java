package cn.alone.transport.proxy;

import cn.alone.transport.model.RpcRequest;
import cn.alone.transport.model.RpcResponse;
import cn.alone.transport.netty.AloneClient;
import cn.alone.transport.netty.channel.ClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by rojeralone on 2018-07-26
 */
public class ObjectProxy implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectProxy.class);

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setRid(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParamTypes(method.getParameterTypes());
        request.setParams(args);
        RpcResponse response = AloneClient.request(request);
        if (response.isSuccess()) {
            return response.getResponseResult();
        }
        LOGGER.error("request failed", response.getException());
        throw response.getException();
    }

}
