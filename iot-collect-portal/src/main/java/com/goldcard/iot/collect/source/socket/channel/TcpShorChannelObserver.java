package com.goldcard.iot.collect.source.socket.channel;

import com.goldcard.iot.collect.IotConstants;
import com.goldcard.iot.collect.observer.AbstractObserver;
import io.netty.channel.ChannelHandlerContext;

public class TcpShorChannelObserver extends AbstractObserver {

    private ChannelHandlerContext ctx;

    public TcpShorChannelObserver(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String sessionId() {
        return IotConstants.IOT_CACHE_SOCKET_SHORT_OBSERVER_KEY + ctx.channel().id().toString();
    }

    @Override
    public void send() {

    }
}
