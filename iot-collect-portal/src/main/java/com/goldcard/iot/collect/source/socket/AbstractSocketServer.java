package com.goldcard.iot.collect.source.socket;

import com.goldcard.iot.collect.configure.SocketServerConfigure;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * socket 服务抽象类
 *
 * @Author 2005
 * @Date 2020/1/29 下午5:06
 */
public abstract class AbstractSocketServer {


    protected ServerBootstrap createServer() {

        ServerBootstrap server = new ServerBootstrap();
        server.channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        return server;

    }


    /**
     * 启动
     *
     * @param configure socket服务配置
     * @Author 2005
     * @Date 2020/1/29 下午5:04
     */
    public abstract void start(SocketServerConfigure configure);
}
