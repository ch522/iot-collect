package com.goldcard.iot.collect.source.http;

import com.goldcard.iot.collect.CmdSendPool;
import com.goldcard.iot.collect.CommandHandler;
import com.goldcard.iot.collect.IotConstants;
import com.goldcard.iot.collect.ProcessHandlerBean;
import com.goldcard.iot.collect.cache.IotCacheManager;
import com.goldcard.iot.collect.rule.ProtocolRule;
import com.goldcard.iot.collect.rule.RuleEngine;
import com.goldcard.iot.collect.util.CommonUtil;
import com.goldcard.iot.collect.util.DateUtil;
import com.goldcard.iot.collect.util.JsonUtil;
import com.goldcard.iot.collect.util.SpringContextHolder;
import io.netty.channel.embedded.EmbeddedChannel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;


@RestController
public class BaseController {

    @Autowired
    private IotCacheManager<String, Object> iotCacheManager;
    @Autowired
    private HttpBizExecutor httpBizExecutor;
    @Autowired
    private CommandHandler commandHandler;
    @Autowired
    RuleEngine engine;
    private static final Logger ERROR_LOG = LoggerFactory.getLogger(BaseController.class);

    @RequestMapping("test/1001")
    public Map<String, Object> test(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(16);
        try {
            Map<String, Object> inMap = JsonUtil.stream2Map(request.getInputStream());
            String sessionId = String.valueOf(inMap.get("deviceId"));
            String hex = String.valueOf(inMap.get("msg"));
            String cmdPoolId = IotConstants.IOT_CACHE_CMD_KEY + sessionId;
//            if (!iotCacheManager.hasKey(cmdPoolId)) {
//                iotCacheManager.put(cmdPoolId, new LinkedList<CmdSendPool>());
//            }
            initialize(inMap);
//            httpBizExecutor.execute(() -> {
//
//
//                try {
//                    ProtocolRule rule = handleRule(sessionId, hex);
//                    if (rule.getHasRegister()) {
//                        if (StringUtils.isNotEmpty(rule.getRegResponse())) {
//                            Map<String, Object> sendMap = new HashMap<>();
//                            sendMap.put("deviceId", sessionId);
//                            sendMap.put("msg", rule.getRegResponse());
//                            //TODO 此处发送指令逻辑
//                            System.out.println("发送指令:" + JsonUtil.obj2Str(sendMap));
//                        }
//                    } else {
//                        if (StringUtils.isBlank(rule.getProtocolCode())) {
//                            throw new RuntimeException("未匹配到协议");
//                        }
//                        ProcessHandlerBean bean = new ProcessHandlerBean();
//                        bean.setSessionId(sessionId);
//                        bean.setProtocolCode(rule.getProtocolCode());
//                        bean.setData(CommonUtil.hex2Byte(hex));
//                        commandHandler.process(bean);
//                        send(sessionId);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    ERROR_LOG.error("出错:", e);
//                }
//            });
            result.put("echoCode", "0000");
            result.put("echoMsg", "成功");
        } catch (Exception e) {
            ERROR_LOG.error("出错:", e);
        }

        return result;
    }


    private void initialize(Map<String, Object> inMap) {
        String sessionId = String.valueOf(inMap.get("deviceId"));
        HttpHandlerContext context = null;
        if (!iotCacheManager.hasKey(sessionId)) {
            HttpInboundHandler handler = SpringContextHolder.getBean(NbIotHandler.class);
            HttpIdleHandler idleHandler = new HttpIdleHandler(30L, TimeUnit.SECONDS);
            context = new DefaultHttpHandlerContext(httpBizExecutor.getExecutor(), handler, idleHandler, sessionId);
            iotCacheManager.put(sessionId, context);
        } else {
            context = (HttpHandlerContext) iotCacheManager.get(sessionId);
        }
        context.fireInvoker(inMap);
    }


    private void send(String sessionId) {
        String cmdCacheId = IotConstants.IOT_CACHE_CMD_KEY + sessionId;
        String protocolCacheId = IotConstants.IOT_CACHE_PROTOCOL_KEY + sessionId;
        Queue<CmdSendPool> queue = (Queue<CmdSendPool>) iotCacheManager.get(cmdCacheId);
        if (null != queue) {
            CmdSendPool pool = queue.poll();
            if (null != pool) {
                byte[] bs = pool.getOutwardCommand().getBytes(new EmbeddedChannel());
                Map<String, Object> sendMap = new HashMap<>();
                sendMap.put("deviceId", sessionId);
                sendMap.put("msg", CommonUtil.byte2Hex(bs));
                //TODO 此处发送数据逻辑
                System.out.println("发送指令:" + JsonUtil.obj2Str(sendMap));
                if (pool.getHasEnd()) {
                    iotCacheManager.remove(cmdCacheId);
                    iotCacheManager.remove(protocolCacheId);
                    System.out.println(DateUtil.formatStr(new Date(), "yyyy-MM-dd HH:mm:ss:sss") + ":通道[" + sessionId + "],通讯成功");
                }
            }
        }
    }


    private ProtocolRule handleRule(String sessionId, String hex) {
        String key = IotConstants.IOT_CACHE_PROTOCOL_KEY + sessionId;
        ProtocolRule rule = (ProtocolRule) iotCacheManager.get(key);
        if (Objects.isNull(rule)) {
            byte[] bs = CommonUtil.hex2Byte(hex);
            rule = new ProtocolRule.Builder().data(bs).
                    hexStr(hex).size(bs.length).build();
            engine.execute(rule);
            if (StringUtils.isNotBlank(rule.getProtocolCode())) {
                iotCacheManager.put(key, rule);
            }
        }
        return rule;
    }
}
