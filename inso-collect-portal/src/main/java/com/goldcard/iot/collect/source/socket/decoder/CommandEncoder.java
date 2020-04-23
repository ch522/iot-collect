package com.goldcard.iot.collect.source.socket.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Description:编码
 *
 * @Author: 3247
 * @Date: 2019/4/2 12:07
 * @Version 1.0
 */
public class CommandEncoder extends MessageToByteEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        out.writeBytes(msg);
        ctx.flush();
    }


}
