package cn.alone.transport.model;

import java.io.Serializable;

/**
 * Created by rojeralone on 2018-07-25
 */
public class RpcRequest implements Serializable {

    private String rid;
    private String className;
    private String methodName;
    private Class<?>[] paramTypes;
    private Object[] params;

    public String getRid() {
        return rid;
    }

    public RpcRequest setRid(String rid) {
        this.rid = rid;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public RpcRequest setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public RpcRequest setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public RpcRequest setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
        return this;
    }

    public Object[] getParams() {
        return params;
    }

    public RpcRequest setParams(Object[] params) {
        this.params = params;
        return this;
    }
}
