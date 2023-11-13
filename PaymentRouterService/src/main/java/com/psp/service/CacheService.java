package com.psp.service;

public interface CacheService {

    Object get(Object key);

    void put(Object key, Object value);

    void invalidCache();

    void validCache();
}
