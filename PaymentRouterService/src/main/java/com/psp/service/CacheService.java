package com.psp.service;

public interface CacheService {

    Object get(String key);

    void put(String key, Object value);

    void invalidCache();

    void validCache();
}
