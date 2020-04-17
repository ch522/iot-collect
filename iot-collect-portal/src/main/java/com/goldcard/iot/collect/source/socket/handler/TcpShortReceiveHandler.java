package com.goldcard.iot.collect.source.socket.handler;

import com.goldcard.iot.collect.CmdSendPool;
import com.goldcard.iot.collect.CommandHandler;
import com.goldcard.iot.collect.IotConstants;
import com.goldcard.iot.collect.ProcessHandlerBean;
import com.goldcard.iot.collect.cache.IotCacheManager;
import com.goldcard.iot.collect.rule.ProtocolRule;
import com.goldcard.iot.collect.rule.RuleEngine;
import com.goldcard.iot.collect.source.socket.channel.TcpShorChannelObserver;
import com.goldcard.iot.collect.observer.ISubject;
import com.goldcard.iot.collect.util.CommonUtil;
import com.goldcard.iot.collect.util.SpringContextHolder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * tcp短连接处理
 *
 * @Author 2005
 * @Date 2020/1/30 上午11:36
 */
public class TcpShortReceiveHandler extends SimpleChannelInboundHandler<byte[]> {
    private static final Logger log = LoggerFactory.getLogger(TcpShortReceiveHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] bytes) throws Exception {
        String sessionId = ctx.channel().id().toString();
        ISubject subject = SpringContextHolder.getBean(ISubject.class);
        IotCacheManager<String, LinkedBlockingQueue<CmdSendPool>> iotCacheManager = SpringContextHolder.getBean("iotCacheManager", IotCacheManager.class);
        String observerId = IotConstants.IOT_CACHE_SOCKET_SHORT_OBSERVER_KEY + sessionId;
        String cmdPoolId = IotConstants.IOT_CACHE_SOCKET_SHORT_CMD_KEY + sessionId;
        ProtocolRule rule = handleRule(sessionId, bytes);
        if (!subject.hasObserver(observerId)) {
            subject.register(new TcpShorChannelObserver(ctx));
        }
        if (!iotCacheManager.getCache().hasKey(cmdPoolId)) {
            iotCacheManager.getCache().put(cmdPoolId, new LinkedBlockingQueue<CmdSendPool>(100));
        }

        if (rule.getHasRegister()) {//注册贞处理
            if (StringUtils.isNotBlank(rule.getRegResponse())) {
                byte[] sendMsg = CommonUtil.hex2Byte(rule.getRegResponse());
                ByteBuf outByteBuf = Unpooled.buffer(sendMsg.length);
                outByteBuf.writeBytes(sendMsg);
                ctx.channel().writeAndFlush(outByteBuf);
            }
        } else {
            if (StringUtils.isBlank(rule.getProtocolCode())) {
                throw new RuntimeException("未匹配到协议");
            }
            ProcessHandlerBean bean = new ProcessHandlerBean();
            bean.setSessionId(sessionId);
            bean.setProtocolCode(rule.getProtocolCode());
            bean.setData(rule.getData());
            CommandHandler handler = SpringContextHolder.getBean(CommandHandler.class);
            handler.process(bean);
            subject.send(observerId);

        }

    }

    private ProtocolRule handleRule(String sessionId, byte[] bs) {
        RuleEngine engine = SpringContextHolder.getBean(RuleEngine.class);
        IotCacheManager<String, ProtocolRule> cacheManager =
                SpringContextHolder.getBean("iotCacheManager", IotCacheManager.class);
        String key = IotConstants.IOT_CACHE_SOCKET_SHORT_PROTOCOL_CODE_KEY + sessionId;
        ProtocolRule rule = cacheManager.getCache().get(key);
        if (Objects.isNull(rule)) {
            rule = new ProtocolRule.Builder().data(bs).
                    hexStr(CommonUtil.byte2Hex(bs)).size(bs.length).build();
            engine.execute(rule);
            if (StringUtils.isNotBlank(rule.getProtocolCode())) {
                cacheManager.getCache().put(key, rule);
            }
        }
        return rule;


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
