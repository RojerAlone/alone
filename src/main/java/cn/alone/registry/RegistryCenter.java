package cn.alone.registry;

import cn.alone.transport.model.RpcRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rojeralone on 2018-07-31
 */
public class RegistryCenter {

    private static final Map<String, Object> registryService = new ConcurrentHashMap<String, Object>();

    public static Object getService(RpcRequest rpcRequest) {
        String registryName = rpcRequest.getClassName() + "." + rpcRequest.getMethodName();
        return registryService.get(registryName);
    }

}
