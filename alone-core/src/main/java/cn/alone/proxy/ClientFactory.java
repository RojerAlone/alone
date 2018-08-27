package cn.alone.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by rojeralone on 2018-07-26
 */
public class ClientFactory {

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(Class clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ObjectProxy());
    }

}
