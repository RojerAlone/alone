package cn.alone.transport.util;

import cn.alone.transport.model.RpcRequest;
import cn.alone.transport.model.RpcResponse;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;

/**
 * 默认反序列化工具，使用 protostuff 进行反序列化
 * @author RojerAlone
 * @Date 2018-07-30 22:03
 */
public class SerializationUtil {

    private static final RuntimeSchema responseSchema = RuntimeSchema.createFrom(RpcResponse.class);
    private static final RuntimeSchema requestSchema = RuntimeSchema.createFrom(RpcRequest.class);

    @SuppressWarnings("unchecked")
    public static <T> T deserialization(byte[] data, Class<T> targetClass) {
        if (data != null && data.length > 0) {
            RuntimeSchema schema = null;
            if (targetClass.equals(RpcRequest.class)) {
                schema = requestSchema;
            } else if (targetClass.equals(RpcResponse.class)) {
                schema = responseSchema;
            } else {
                throw new IllegalArgumentException("unsupport serialize class : " + targetClass.getName());
            }
            Object obj = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(data, obj, schema);
            return (T) obj;
        }
        return null;
    }

    public static byte[] serialization(Object obj, Class targetClass) {
        RuntimeSchema schema = null;
        if (targetClass.equals(RpcRequest.class)) {
            schema = requestSchema;
        } else if (targetClass.equals(RpcResponse.class)) {
            schema = responseSchema;
        } else {
            throw new IllegalArgumentException("unsupport serialize class : " + targetClass.getName());
        }
        return ProtostuffIOUtil.toByteArray(obj, schema, LinkedBuffer.allocate());
    }

}
