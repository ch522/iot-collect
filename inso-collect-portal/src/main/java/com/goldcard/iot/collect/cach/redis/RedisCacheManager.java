package com.goldcard.iot.collect.cach.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldcard.iot.collect.cache.IotCache;
import com.goldcard.iot.collect.cache.IotCacheManager;
import com.goldcard.iot.collect.util.SpringContextHolder;
import net.sf.ehcache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class RedisCacheManager<K, V> implements IotCacheManager<K, V> {
    private static JedisConnectionFactory factory;
    private RedisTemplate<K, V> redisTemplate;

    @Autowired
    RedisProperties properties;

    @PostConstruct
    private void init() {
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
        RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        factory.afterPropertiesSet();
        redisTemplate = template;
    }

    private RedisCacheManager() {


    }

    @Override
    public V get(K var1) {
        try {

            if (var1 == null) {
                return null;
            } else {
                Object v = this.redisTemplate.opsForValue().get(var1);
                if (null == v) {
                    return null;
                }
                return (V) v;
            }
        } catch (Throwable var3) {
            throw new CacheException(var3);
        }
    }

    @Override
    public V put(K var1, V var2) {
        try {
            V previous = this.get(var1);
            this.redisTemplate.opsForValue().set(var1, var2);
            return previous;
        } catch (Throwable var5) {
            throw new CacheException(var5);
        }
    }

    @Override
    public V remove(K var1) {
        try {
            if (null == var1) {
                return null;
            } else {
                V previous = this.get(var1);
                this.redisTemplate.delete(var1);
                return previous;
            }
        } catch (Throwable var3) {
            throw new CacheException(var3);
        }

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public void clear() {
        throw new RuntimeException("未实现该方法");
    }


    @Override
    public Boolean hasKey(K k) {
        try {
            return this.redisTemplate.hasKey(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

}
