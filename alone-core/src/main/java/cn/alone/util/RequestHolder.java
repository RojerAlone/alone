package cn.alone.util;

import cn.alone.model.RpcFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author RojerAlone
 * @Date 2018-08-12 14:36
 */
public class RequestHolder {

    private static final Map<String, RpcFuture> reqMap = new ConcurrentHashMap<String, RpcFuture>();

    public static RpcFuture get(String reqId) {
        return reqMap.get(reqId);
    }

    public static void put(String reqId, RpcFuture future) {
        reqMap.put(reqId, future);
    }

    public static void remove(String reqId) {
        reqMap.remove(reqId);
    }

}
