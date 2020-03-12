package com.goldcard.iot.collect.cach.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldcard.iot.collect.cache.IotCache;
import com.goldcard.iot.collect.cache.IotCacheManager;
import com.goldcard.iot.collect.util.SpringContextHolder;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

public class RedisCacheManager<K, V> implements IotCacheManager<K, V> {
    static JedisConnectionFactory factory;


    static {
        RedisProperties properties = SpringContextHolder.getBean(RedisProperties.class);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        RedisProperties.Pool pool = properties.getPool();
        if (null != pool) {
            poolConfig.setMaxIdle(pool.getMaxIdle());
            poolConfig.setMaxWaitMillis(pool.getMaxWait());
        }

        RedisSentinelConfiguration sentinelConfig = getSentinelConfiguration(properties);
        RedisClusterConfiguration clusterConfiguration = getClusterConfiguration(properties);
        if (sentinelConfig != null) {
            factory = new JedisConnectionFactory(sentinelConfig, poolConfig);
        } else if (clusterConfiguration != null) {
            factory = new JedisConnectionFactory(clusterConfiguration, poolConfig);
        } else {
            factory = new JedisConnectionFactory(poolConfig);
            factory.setHostName(properties.getHost());
            factory.setPort(properties.getPort());
            factory.setDatabase(properties.getDatabase());
        }

        if (org.apache.commons.lang.StringUtils.isNotBlank(properties.getPassword())) {
            factory.setPassword(properties.getPassword());
        }


    }

    private static List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {
        List<RedisNode> nodes = new ArrayList<>();
        for (String node : StringUtils.commaDelimitedListToStringArray(sentinel.getNodes())) {
            String[] parts = StringUtils.split(node, ":");
            Assert.state(parts.length == 2, "redis哨兵地址配置不合法！");
            nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
        }
        return nodes;
    }

    private static RedisSentinelConfiguration getSentinelConfiguration(RedisProperties properties) {
        RedisProperties.Sentinel sentinel = properties.getSentinel();
        if (sentinel != null) {
            RedisSentinelConfiguration config = new RedisSentinelConfiguration();
            config.master(sentinel.getMaster());
            config.setSentinels(createSentinels(sentinel));
            return config;
        }
        return null;
    }


    private static RedisClusterConfiguration getClusterConfiguration(RedisProperties properties) {
        RedisProperties.Cluster cluster = properties.getCluster();
        if (cluster != null) {
            RedisClusterConfiguration config = new RedisClusterConfiguration(cluster.getNodes());
            if (cluster.getMaxRedirects() != null) {
                config.setMaxRedirects(cluster.getMaxRedirects());
            }
            return config;
        }
        return null;
    }

    @Override
    public IotCache<K, V> getCache() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);

        template.setValueSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return new RedisCache(template);
    }
}
