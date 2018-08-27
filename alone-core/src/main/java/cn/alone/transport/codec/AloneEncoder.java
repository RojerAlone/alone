package cn.alone.transport.codec;

import cn.alone.transport.serialize.Serialization;
import cn.alone.transport.serialize.impl.DefaultSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by rojeralone on 2018-07-31
 */
public class AloneEncoder extends MessageToByteEncoder {

    private static Serialization serializationUtil = new DefaultSerialization();

    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeBytes(serializationUtil.serialize(msg));
    }

}
