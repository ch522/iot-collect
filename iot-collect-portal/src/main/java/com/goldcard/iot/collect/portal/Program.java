package com.goldcard.iot.collect.portal;


import com.goldcard.iot.collect.rule.ProtocolRule;
import com.goldcard.iot.collect.rule.RuleEngine;
import com.goldcard.iot.collect.source.InitSocketSource;
import com.goldcard.iot.collect.util.CommonUtil;
import com.goldcard.iot.collect.util.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = {
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class
})
@EnableConfigurationProperties({RedisProperties.class})
@ImportResource("classpath:spring/spring-core-conf.xml")
public class Program {
    public static void main(String[] args) {



        SpringApplication.run(Program.class, args);

        Boolean flag = SpringContextHolder.containsBean("iotCacheManager");

        InitSocketSource initSocketSource = new InitSocketSource();
        initSocketSource.init();
        RuleEngine engine = SpringContextHolder.getBean(RuleEngine.class);
        byte[] bs = new byte[]{0x7A, 0x73};

        ProtocolRule bo = new ProtocolRule.Builder().data(bs).
                size(bs.length).hexStr(CommonUtil.byte2Hex(bs)).build();

        engine.execute(bo);
        System.out.println(bo.getProtocolCode());


        System.out.println("=====SpringBoot启动");
    }
}
