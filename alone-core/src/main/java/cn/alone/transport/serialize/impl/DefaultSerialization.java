package cn.alone.transport.serialize.impl;

import cn.alone.model.RpcRequest;
import cn.alone.model.RpcResponse;
import cn.alone.transport.serialize.Serialization;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Created by rojeralone on 2018-08-13
 */
public class DefaultSerialization implements Serialization {

    private static final RuntimeSchema responseSchema = RuntimeSchema.createFrom(RpcResponse.class);
    private static final RuntimeSchema requestSchema = RuntimeSchema.createFrom(RpcRequest.class);

    public byte[] serialize(Object object) {
        Class clazz = null;
        if (object instanceof RpcRequest) {
            clazz = RpcRequest.class;
        } else if (object instanceof RpcResponse) {
            clazz = RpcResponse.class;
        }
        return serialization(object, clazz);
    }

    public Object deserialize(byte[] bytes, Class clazz) {
        return deserialization(bytes, clazz);
    }

    private byte[] serialization(Object obj, Class targetClass) {
        return ProtostuffIOUtil.toByteArray(obj, getSchema(targetClass), LinkedBuffer.allocate());
    }

    @SuppressWarnings("unchecked")
    private <T> T deserialization(byte[] data, Class<T> targetClass) {
        if (data != null && data.length > 0) {
            RuntimeSchema schema = getSchema(targetClass);
            Object obj = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(data, obj, schema);
            return (T) obj;
        }
        return null;
    }

    private RuntimeSchema getSchema(Class clazz) {
        if (RpcRequest.class.equals(clazz)) {
            return requestSchema;
        } else if (RpcResponse.class.equals(clazz)) {
            return responseSchema;
        } else {
            throw new IllegalArgumentException("unsupport serialize class : " + clazz.getName());
        }
    }

}
