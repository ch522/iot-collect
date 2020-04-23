package com.goldcard.iot.collect.cach.eh;

import com.goldcard.iot.collect.cache.IotCache;
import com.goldcard.iot.collect.cache.IotCacheManager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

public class EhCacheManager<K, V> implements IotCacheManager<K, V> {
    private static final String CACHE_NAME = "iot-cache";
    private static CacheManager manager = null;
    private Cache cache = null;

    private EhCacheManager() {
        try {
            File file = ResourceUtils.getFile("classpath:cache/ehcache.xml");
            InputStream stream = new FileInputStream(file);
            manager = CacheManager.create(stream);
            cache = manager.getCache(CACHE_NAME);
        } catch (Exception e) {
            System.out.println("Ehcache初始化失败");
            e.printStackTrace();
        }
    }

    @Override
    public V get(K var1) {
        try {
            if (var1 == null) {
                return null;
            } else {
                Element element = cache.get(var1);
                if (element == null) {
                    return null;
                } else {
                    return (V) element.getObjectValue();
                }
            }
        } catch (Throwable var3) {
            throw new CacheException(var3);
        }

    }

    @Override
    public V put(K var1, V var2) {
        try {
            V previous = this.get(var1);
            Element element = new Element(var1, var2);
            cache.put(element);
            return previous;
        } catch (Throwable var5) {
            throw new CacheException(var5);
        }
    }

    @Override
    public V remove(K var1) {
        try {
            V previous = this.get(var1);
            cache.remove(var1);
            return previous;
        } catch (Throwable var3) {
            throw new CacheException(var3);
        }
    }

    @Override
    public void clear() {
        try {
            cache.removeAll();
        } catch (Throwable var2) {
            throw new CacheException(var2);
        }
    }

    @Override
    public int size() {
        try {
            return cache.getSize();
        } catch (Throwable var2) {
            throw new CacheException(var2);
        }
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
    public Boolean hasKey(K k) {
        return cache.isKeyInCache(k);
    }
}
