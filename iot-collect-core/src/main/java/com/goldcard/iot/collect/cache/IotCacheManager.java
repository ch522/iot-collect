package com.goldcard.iot.collect.cache;

public interface IotCacheManager<K, V> {
    IotCache<K, V> getCache();
}
