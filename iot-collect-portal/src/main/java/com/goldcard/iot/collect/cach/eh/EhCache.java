package com.goldcard.iot.collect.cach.eh;

import com.goldcard.iot.collect.cache.IotCache;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import java.util.Collection;
import java.util.Set;

public class EhCache<K, V> implements IotCache<K, V> {
    private Cache cache;

    public EhCache(Cache cache) {
        if (cache == null) {
            throw new IllegalArgumentException("EhCache argument cannot be null.");
        } else {
            this.cache = cache;
        }
    }

    @Override
    public V get(K var1) {
        try {
            if (var1 == null) {
                return null;
            } else {
                Element element = this.cache.get(var1);
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
            this.cache.put(element);
            return previous;
        } catch (Throwable var5) {
            throw new CacheException(var5);
        }
    }

    @Override
    public V remove(K var1) {
        try {
            V previous = this.get(var1);
            this.cache.remove(var1);
            return previous;
        } catch (Throwable var3) {
            throw new CacheException(var3);
        }
    }

    @Override
    public void clear() {
        try {
            this.cache.removeAll();
        } catch (Throwable var2) {
            throw new CacheException(var2);
        }
    }

    @Override
    public int size() {
        try {
            return this.cache.getSize();
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
        return this.cache.isKeyInCache(k);
    }
}
