package com.goldcard.iot.collect.source.socket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * TCP短连接超时处理
 *
 * @Author 2005
 * @Date 2020/1/30 上午11:34
 */
public class TcpShortChannelInboundHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        ctx.flush();
        ctx.close();
    }
}
