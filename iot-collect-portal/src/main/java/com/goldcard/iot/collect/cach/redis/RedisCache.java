package com.goldcard.iot.collect.cach.redis;

import com.goldcard.iot.collect.cache.IotCache;
import net.sf.ehcache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

public class RedisCache<K, V> implements IotCache<K, V> {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
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
            this.redisTemplate.opsForValue().set(var1.toString(), var2);
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
                this.redisTemplate.delete(var1.toString());
                return previous;
            }
        } catch (Throwable var3) {
            throw new CacheException(var3);
        }

    }

    @Override
    public void clear() {
        throw new RuntimeException("未实现该方法");
    }

    @Override
    public int size() {
        throw new RuntimeException("未实现该方法");
    }

    @Override
    public Set<K> keys() {
        throw new RuntimeException("未实现该方法");
    }

    @Override
    public Collection<V> values() {
        throw new RuntimeException("未实现该方法");
    }

    @Override
    public Boolean hasKey(K k) {
        return this.redisTemplate.hasKey(k.toString());
    }
}
