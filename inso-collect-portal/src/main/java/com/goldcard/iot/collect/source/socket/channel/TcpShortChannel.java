package com.goldcard.iot.collect.source.socket.channel;

import com.goldcard.iot.collect.configure.SocketServerConfigure;
import com.goldcard.iot.collect.source.socket.decoder.CommandDecoder;
import com.goldcard.iot.collect.source.socket.decoder.CommandEncoder;
import com.goldcard.iot.collect.source.socket.handler.TcpShortReceiveHandler;
import com.goldcard.iot.collect.util.SpringContextHolder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.TimeUnit;


public class TcpShortChannel extends ChannelInitializer<SocketChannel> {

    private SocketServerConfigure configure;

    public TcpShortChannel(SocketServerConfigure configure) {
        super();
        this.configure = configure;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new IdleStateHandler(configure.getTimeOut(), 0, 0, TimeUnit.SECONDS));
        socketChannel.pipeline().addLast(new CommandDecoder());
        socketChannel.pipeline().addLast(new CommandEncoder());
        socketChannel.pipeline().addLast(configure.getHandler());
    }
}
