package com.goldcard.iot.collect.source.socket.handler;

import com.goldcard.iot.collect.CmdSendPool;
import com.goldcard.iot.collect.CommandHandler;
import com.goldcard.iot.collect.IotConstants;
import com.goldcard.iot.collect.ProcessHandlerBean;
import com.goldcard.iot.collect.cache.IotCacheManager;
import com.goldcard.iot.collect.rule.ProtocolRule;
import com.goldcard.iot.collect.rule.RuleEngine;
import com.goldcard.iot.collect.util.CommonUtil;
import com.goldcard.iot.collect.util.DateUtil;
import com.goldcard.iot.collect.util.SpringContextHolder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import io.netty.channel.ChannelHandler.Sharable;

/**
 * tcp短连接处理
 *
 * @Author 2005
 * @Date 2020/1/30 上午11:36
 */
@Component
@Scope("prototype")
@Sharable
public class TcpShortReceiveHandler extends SimpleChannelInboundHandler<byte[]> {
    private static final Logger ERROR = LoggerFactory.getLogger(TcpShortReceiveHandler.class);
    @Autowired
    private IotCacheManager<String, Object> iotCacheManager;
    @Autowired
    private RuleEngine engine;
    @Autowired
    private CommandHandler commandHandler;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] bytes) throws Exception {
        byte[] data = Arrays.copyOfRange(bytes, 0, bytes.length);
        String sessionId = ctx.channel().id().toString();
        ProtocolRule rule = handleRule(sessionId, data);
        if (rule.getHasRegister()) {//注册贞处理
//                System.out.println(DateUtil.formatStr(new Date(), "yyyy-MM-dd HH:mm:ss:sss") + ":通道[" + sessionId + "],注册帧[" + CommonUtil.byte2Hex(data) + "]");
            if (StringUtils.isNotBlank(rule.getRegResponse())) {
                byte[] sendMsg = CommonUtil.hex2Byte(rule.getRegResponse());
                ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(sendMsg));
//                    System.out.println(DateUtil.formatStr(new Date(), "yyyy-MM-dd HH:mm:ss:sss") + ":通道[" + sessionId + "],注册帧返回[" + rule.getRegResponse() + "]");
            }
        } else {
//                System.out.println(DateUtil.formatStr(new Date(), "yyyy-MM-dd HH:mm:ss:sss") + ":通道[" + sessionId + "],首帧[" + CommonUtil.byte2Hex(data) + "]");
            if (StringUtils.isBlank(rule.getProtocolCode())) {
                throw new RuntimeException("未匹配到协议");
            }
            ProcessHandlerBean bean = new ProcessHandlerBean();
            bean.setSessionId(sessionId);
            bean.setProtocolCode(rule.getProtocolCode());
            bean.setData(rule.getData());
            commandHandler.process(bean);
            send(ctx);
//            System.out.println(DateUtil.formatStr(new Date(), "yyyy-MM-dd HH:mm:ss:sss") + ":通道[" + sessionId + "],处理结束[" + CommonUtil.byte2Hex(bytes) + "]");
        }


    }

    private void send(ChannelHandlerContext ctx) {
        String id = ctx.channel().id().toString();
        String cmdCacheId = IotConstants.IOT_CACHE_CMD_KEY + ctx.channel().id().toString();
        String protocolCacheId = IotConstants.IOT_CACHE_PROTOCOL_KEY + ctx.channel().id().toString();
        Queue<CmdSendPool> queue = (Queue<CmdSendPool>) iotCacheManager.get(cmdCacheId);
        if (null != queue) {
            CmdSendPool pool = queue.poll();
            if (null != pool) {
                byte[] bs = pool.getOutwardCommand().getBytes(new EmbeddedChannel());
                try {
                    ChannelFuture future = ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(bs)).sync();
                    if (future.isSuccess()) {
                        if (pool.getHasEnd()) {
                            iotCacheManager.remove(cmdCacheId);
                            iotCacheManager.remove(protocolCacheId);
                            ctx.channel().close();
                            System.out.println(DateUtil.formatStr(new Date(), "yyyy-MM-dd HH:mm:ss:sss") + ":通道[" + id + "],通讯成功");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ProtocolRule handleRule(String sessionId, byte[] bs) {
        String key = IotConstants.IOT_CACHE_PROTOCOL_KEY + sessionId;
        ProtocolRule rule = (ProtocolRule) iotCacheManager.get(key);
        if (Objects.isNull(rule)) {
            rule = new ProtocolRule.Builder().data(bs).
                    hexStr(CommonUtil.byte2Hex(bs)).size(bs.length).build();
            engine.execute(rule);
            if (StringUtils.isNotBlank(rule.getProtocolCode())) {
                iotCacheManager.put(key, rule);
            }
        }
        return rule;
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ERROR.error("出错:[" + ctx.channel().id().toString() + "]", cause);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        String sessionId = ctx.channel().id().toString();
        String cmdCacheId = IotConstants.IOT_CACHE_CMD_KEY + sessionId;
        String protocolCacheId = IotConstants.IOT_CACHE_PROTOCOL_KEY + sessionId;
        iotCacheManager.remove(cmdCacheId);
        iotCacheManager.remove(protocolCacheId);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String sessionId = ctx.channel().id().toString();
        String cmdPoolId = IotConstants.IOT_CACHE_CMD_KEY + sessionId;
        if (!iotCacheManager.hasKey(cmdPoolId)) {
            iotCacheManager.put(cmdPoolId, new LinkedList<CmdSendPool>());
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        try {
            super.userEventTriggered(ctx, evt);
            System.out.println("超时未发送数据");
            ctx.flush();
            ctx.close();
        } catch (Exception e) {
            ERROR.error("出错:", e);
        }
    }
}
