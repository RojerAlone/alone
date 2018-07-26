package cn.alone.transport.model;

import java.io.Serializable;

/**
 * Created by rojeralone on 2018-07-25
 */
public class RpcResponse implements Serializable{

    private String rid;
    private String error;
    private Class<?> responseType;
    private Object responseResult;

    public String getRid() {
        return rid;
    }

    public RpcResponse setRid(String rid) {
        this.rid = rid;
        return this;
    }

    public String getError() {
        return error;
    }

    public RpcResponse setError(String error) {
        this.error = error;
        return this;
    }

    public Class<?> getResponseType() {
        return responseType;
    }

    public RpcResponse setResponseType(Class<?> responseType) {
        this.responseType = responseType;
        return this;
    }

    public Object getResponseResult() {
        return responseResult;
    }

    public RpcResponse setResponseResult(Object responseResult) {
        this.responseResult = responseResult;
        return this;
    }
}
