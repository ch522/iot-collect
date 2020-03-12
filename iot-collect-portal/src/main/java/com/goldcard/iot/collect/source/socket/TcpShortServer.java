package com.goldcard.iot.collect.source.socket;

import com.goldcard.iot.collect.configure.SocketServerConfigure;
import com.goldcard.iot.collect.source.socket.channel.TcpShortChannel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * tcp短连接服务
 *
 * @Author 2005
 * @Date 2020/1/31 下午7:38
 */
public class TcpShortServer extends AbstractSocketServer {
    @Override
    public void start(SocketServerConfigure configure) {
        ServerBootstrap server = super.createServer();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            server.group(bossGroup, workGroup).childHandler(new TcpShortChannel(Long.parseLong(configure.getTimeOut().toString())));
            ChannelFuture future = server.bind(configure.getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
