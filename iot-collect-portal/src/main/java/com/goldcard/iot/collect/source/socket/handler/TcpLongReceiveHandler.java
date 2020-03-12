package com.goldcard.iot.collect.source.socket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * tcp长连接处理
 *
 * @Author 2005
 * @Date 2020/1/30 上午11:36
 */
public class TcpLongReceiveHandler extends SimpleChannelInboundHandler<byte[]> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, byte[] bytes) throws Exception {

    }
}
