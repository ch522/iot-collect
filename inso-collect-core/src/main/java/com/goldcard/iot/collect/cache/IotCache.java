package com.goldcard.iot.collect.cache;

import java.util.Collection;
import java.util.Set;

public interface IotCache<K, V> {
    V get(K var1);

    V put(K var1, V var2);

    V remove(K var1);

    void clear();

    Boolean hasKey(K k);
}
