package cn.alone.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by rojeralone on 2018-07-26
 */
public class ObjectProxy implements InvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(proxy, args);
    }

}
