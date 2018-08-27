package cn.alone.registry;

import cn.alone.model.RpcRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rojeralone on 2018-07-31
 */
public class RegistryCenter {

    private static final Map<String, Object> registryService = new ConcurrentHashMap<String, Object>();

    public static Object getService(RpcRequest rpcRequest) {
        String registryName = rpcRequest.getClassName();
        return registryService.get(registryName);
    }

    public static void registry(Class interfaceClass, Object serviceImpl) {
        registryService.put(interfaceClass.getName(), serviceImpl);
    }

}
