package cn.alone.transport.netty.codec;

import cn.alone.transport.model.RpcRequest;
import cn.alone.transport.model.RpcResponse;
import cn.alone.transport.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by rojeralone on 2018-07-31
 */
public class AloneEncoder extends MessageToByteEncoder {

    private final Class encodeClass;

    public AloneEncoder(boolean isRequest) {
        if (isRequest) {
            encodeClass = RpcRequest.class;
        } else {
            encodeClass = RpcResponse.class;
        }
    }

    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeBytes(SerializationUtil.serialization(msg, encodeClass));
    }

}
