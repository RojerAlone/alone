package cn.alone.transport.netty.codec;

import cn.alone.transport.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by rojeralone on 2018-07-31
 */
public class ResponseEncoder extends MessageToByteEncoder {

    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeBytes(SerializationUtil.serialization(msg));
    }

}
