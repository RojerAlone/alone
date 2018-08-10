package cn.alone.service;

import cn.alone.registry.RegistryCenter;
import cn.alone.transport.model.RpcRequest;
import cn.alone.transport.model.RpcResponse;

import java.lang.reflect.Method;

/**
 * Created by rojeralone on 2018-08-10
 */
public class ServiceProvider {

    public static RpcResponse request(RpcRequest request) {
        RpcResponse response = new RpcResponse();
        response.setRid(request.getRid());
        doInvoke(request, response);
        return response;
    }

    private static void doInvoke(final RpcRequest request, final RpcResponse response) {
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
