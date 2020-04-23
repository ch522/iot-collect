package com.goldcard.iot.collect.source.socket.channel;

import com.goldcard.iot.collect.CmdSendPool;
import com.goldcard.iot.collect.IotConstants;
import com.goldcard.iot.collect.cache.IotCacheManager;
import com.goldcard.iot.collect.observer.AbstractObserver;
import com.goldcard.iot.collect.util.CommonUtil;
import com.goldcard.iot.collect.util.DateUtil;
import com.goldcard.iot.collect.util.SpringContextHolder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;

import java.util.Date;
import java.util.Queue;

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
        String cmdCacheId = IotConstants.IOT_CACHE_CMD_KEY + ctx.channel().id().toString();
        String protocolCacheId = IotConstants.IOT_CACHE_PROTOCOL_KEY + ctx.channel().id().toString();
        Queue<CmdSendPool> queue = (Queue<CmdSendPool>) iotCacheManager.get(cmdCacheId);
        if (null != queue) {
            CmdSendPool pool = queue.poll();
            //   int i = iotCacheManager.getCache().get(cmdCacheId).size();
            if (null != pool) {
                byte[] bs = pool.getOutwardCommand().getBytes(new EmbeddedChannel());
                try {

                    ChannelFuture future = ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(bs)).sync();
                    if (future.isSuccess()) {
                        if (pool.getHasEnd()) {
                            iotCacheManager.remove(cmdCacheId);
                            iotCacheManager.remove(sessionId());
                            iotCacheManager.remove(protocolCacheId);
                            ctx.channel().close();
                            System.out.println(DateUtil.formatStr(new Date(), "yyyy-MM-dd HH:mm:ss:sss") + ":通道[" + id + "],发送的数据[" + CommonUtil.byte2Hex(bs) + "]");
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
