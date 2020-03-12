package com.goldcard.iot.collect.source.socket.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class CommandDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] bs = buf2byte(byteBuf);
        list.add(bs);
    }

    private byte[] buf2byte(ByteBuf in) {
        byte[] reqMsgArr = null;
        if (!in.hasArray()) {
            int len = in.readableBytes();
            reqMsgArr = new byte[len];
            int isOrNo = in.refCnt();
            if (isOrNo == 0) {
                return null;
            }
            in.getBytes(0, reqMsgArr);
        } else {
            reqMsgArr = in.array();
        }
        return reqMsgArr;
    }
}
