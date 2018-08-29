package cn.alone.transport.codec;

import cn.alone.model.RpcRequest;
import cn.alone.model.RpcResponse;
import cn.alone.transport.serialize.Serialization;
import cn.alone.transport.serialize.impl.DefaultSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author RojerAlone
 * @Date 2018-07-29 16:09
 */
public class AloneDecoder extends ByteToMessageDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AloneDecoder.class);

    private final Class decodeClass;

    private static Serialization serializationUtil = new DefaultSerialization();

    public AloneDecoder(boolean isRequest) {
        if (isRequest) {
            decodeClass = RpcRequest.class;
        } else {
            decodeClass = RpcResponse.class;
        }
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        out.add(serializationUtil.deserialize(bytes, decodeClass));
    }
}