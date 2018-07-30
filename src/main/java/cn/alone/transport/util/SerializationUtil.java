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

    private static final RuntimeSchema decodeSchema = RuntimeSchema.createFrom(RpcResponse.class);
    private static final RuntimeSchema encodeSchema = RuntimeSchema.createFrom(RpcRequest.class);

    public static RpcResponse deserialization(byte[] data) {
        if (data != null && data.length > 0) {
            Object obj = decodeSchema.newMessage();
            ProtostuffIOUtil.mergeFrom(data, obj, decodeSchema);
            return (RpcResponse) obj;
        }
        return null;
    }

    public static byte[] serialization(Object obj) {
        return ProtostuffIOUtil.toByteArray(obj, encodeSchema, LinkedBuffer.allocate());
    }

}
