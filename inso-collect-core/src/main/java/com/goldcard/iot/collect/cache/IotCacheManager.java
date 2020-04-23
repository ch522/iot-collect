package com.goldcard.iot.collect.cache;

import java.util.Collection;
import java.util.Set;

public interface IotCacheManager<K, V> extends IotCache<K, V> {
    int size();

    Set<K> keys();

    Collection<V> values();

}
