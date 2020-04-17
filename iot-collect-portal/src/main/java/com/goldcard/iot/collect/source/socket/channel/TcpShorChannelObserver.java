package com.goldcard.iot.collect.source.socket.channel;

import com.goldcard.iot.collect.CmdSendPool;
import com.goldcard.iot.collect.IotConstants;
import com.goldcard.iot.collect.cache.IotCacheManager;
import com.goldcard.iot.collect.observer.AbstractObserver;
import com.goldcard.iot.collect.util.CommonUtil;
import com.goldcard.iot.collect.util.SpringContextHolder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;

import java.util.concurrent.LinkedBlockingQueue;

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
        String id = ctx.channel().id().toString();
        IotCacheManager<String, Object> iotCacheManager = SpringContextHolder.getBean("iotCacheManager", IotCacheManager.class);
        String cmdCacheId = IotConstants.IOT_CACHE_SOCKET_SHORT_CMD_KEY + ctx.channel().id().toString();
        LinkedBlockingQueue<CmdSendPool> queue = (LinkedBlockingQueue<CmdSendPool>) iotCacheManager.getCache().get(cmdCacheId);
        if (null != queue) {
            CmdSendPool pool = queue.poll();
            //   int i = iotCacheManager.getCache().get(cmdCacheId).size();
            if (null != pool) {
                byte[] bs = pool.getOutwardCommand().getBytes(new EmbeddedChannel());
                try {
                    System.out.println("发送的数据:" + CommonUtil.byte2Hex(bs));
                    ChannelFuture future = ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(bs)).sync();
                    if (future.isSuccess()) {
                        if (pool.getHasEnd()) {
                            iotCacheManager.getCache().remove(cmdCacheId);
                            iotCacheManager.getCache().remove(sessionId());
                            ctx.channel().close();
                        }
                    }
//                    ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(bs));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
