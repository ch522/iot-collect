package com.goldcard.iot.collect.source.socket;

import com.goldcard.iot.collect.configure.SocketServerConfigure;
import com.goldcard.iot.collect.source.socket.channel.TcpShortChannel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * tcp短连接服务
 *
 * @Author 2005
 * @Date 2020/1/31 下午7:38
 */
public class TcpShortServer {
    private static final NioEventLoopGroup WORK_GROUP = new NioEventLoopGroup();

    public void init(SocketServerConfigure serverInfo) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, WORK_GROUP)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64, 2048, 65536))
                    .childHandler(new TcpShortChannel(serverInfo))
                    .option(ChannelOption.SO_BACKLOG, 4096)
                    .option(ChannelOption.SO_REUSEADDR, true);
            ChannelFuture chf = b.bind(serverInfo.getPort()).sync();
            chf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            WORK_GROUP.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
