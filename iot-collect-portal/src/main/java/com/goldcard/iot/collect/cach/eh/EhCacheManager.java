package com.goldcard.iot.collect.cach.eh;

import com.goldcard.iot.collect.cache.IotCache;
import com.goldcard.iot.collect.cache.IotCacheManager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class EhCacheManager<K, V> implements IotCacheManager<K, V> {

    private static final String CACHE_NAME = "iot-cache";
    private static CacheManager manager = null;
    //private static Cache cache = null;

    static {
        try {
            File file = ResourceUtils.getFile("classpath:cache/ehcache.xml");
            InputStream stream = new FileInputStream(file);
            manager = CacheManager.create(stream);
        } catch (Exception e) {
            System.out.println("Ehcache初始化失败");
            e.printStackTrace();
        }
    }

    @Override
    public IotCache<K, V> getCache() {
        Cache cache = manager.getCache(CACHE_NAME);
        return new EhCache<>(cache);
    }
}
