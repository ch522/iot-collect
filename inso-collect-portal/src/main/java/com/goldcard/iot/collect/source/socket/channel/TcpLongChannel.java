package com.goldcard.iot.collect.source.socket.channel;

import com.goldcard.iot.collect.source.socket.decoder.CommandDecoder;
import com.goldcard.iot.collect.source.socket.handler.TcpLongChannelInboundHandler;
import com.goldcard.iot.collect.source.socket.handler.TcpLongReceiveHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class TcpLongChannel extends ChannelInitializer<SocketChannel> {
    private Long timeOut = 180L;
//    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(10);

    public TcpLongChannel(Long timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new IdleStateHandler(timeOut, 0,
                0, TimeUnit.SECONDS));
        socketChannel.pipeline().addLast(new CommandDecoder());
        socketChannel.pipeline().addLast(new TcpLongChannelInboundHandler());
        socketChannel.pipeline().addLast(new TcpLongReceiveHandler());
    }
}
