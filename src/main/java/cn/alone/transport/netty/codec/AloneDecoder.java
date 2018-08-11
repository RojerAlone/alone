package cn.alone.transport.netty.codec;

import cn.alone.transport.model.RpcRequest;
import cn.alone.transport.model.RpcResponse;
import cn.alone.transport.util.SerializationUtil;
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
        out.add(SerializationUtil.deserialization(bytes, decodeClass));
    }
}
