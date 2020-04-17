package com.goldcard.iot.collect.portal;


import com.goldcard.ProtocolResolve;
import com.goldcard.iot.collect.CommandHandler;
import com.goldcard.iot.collect.ProcessHandlerBean;
import com.goldcard.iot.collect.rule.ProtocolRule;
import com.goldcard.iot.collect.rule.RuleEngine;
import com.goldcard.iot.collect.source.InitSocketSource;
import com.goldcard.iot.collect.util.CommonUtil;
import com.goldcard.iot.collect.util.SpringContextHolder;
import com.goldcard.protocol.InwardCommand;
import com.goldcard.protocol.jk.jk16.JK16Protocol;
import com.goldcard.resolve.util.AnalysisUtil;
import com.goldcard.resolve.util.ChannelUtil;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

import java.net.SocketAddress;

@SpringBootApplication(exclude = {
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class
})
@EnableConfigurationProperties({RedisProperties.class})
@ImportResource("classpath:spring/spring-core-conf.xml")
public class Program {
    public static void main(String[] args) {

        AnalysisUtil.initClassInfo();
        SpringApplication.run(Program.class, args);

        byte[] bs = CommonUtil.hex2Byte("7A72B91F0000000000000028000100000001010BCD1E5B06D2EFC400000000333333433333335333333363333333733333338333333393333333A3333333B333333433333334433333345333333463333334733333348333333493333334A3333334B333333533333335433333355333333563333335733333358333333593333335A3333335B333333633333336433333365333333663333336730000000000000000000000000000804000000016059010141016141927004884F2AA");
        RuleEngine engine = SpringContextHolder.getBean(RuleEngine.class);


        ProtocolRule bo = new ProtocolRule.Builder().data(bs).
                size(bs.length).hexStr(CommonUtil.byte2Hex(bs)).build();

        engine.execute(bo);
        System.out.println(bo.getProtocolCode());

        InwardCommand command = (InwardCommand) ProtocolResolve.bytes2Object(bs, new JK16Protocol(), new EmbeddedChannel());

        String no = command.getDeviceNo();
        System.out.println(no);
//        Boolean flag = SpringContextHolder.containsBean("iotCacheManager");
//
        InitSocketSource initSocketSource = new InitSocketSource();
        initSocketSource.init();



//        ProcessHandlerBean bean = new ProcessHandlerBean() {{
//            setData(bs);
//            setProtocolCode("JK10");
//            setSessionId("1111");
//        }};
//        CommandHandler handler = SpringContextHolder.getBean(CommandHandler.class);
//        handler.process(bean);


        System.out.println("=====SpringBoot启动");
    }
}
