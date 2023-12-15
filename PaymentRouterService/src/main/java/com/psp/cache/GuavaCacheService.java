package com.psp.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.psp.service.BankInfoService;
import com.psp.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class GuavaCacheService implements CacheService {

    private AtomicBoolean valid;

    private BankInfoService bankInfoService;

    private LoadingCache<String, Object> cache;

    @Autowired
    public GuavaCacheService(BankInfoService bankInfoService) {
        this.bankInfoService = bankInfoService;
        valid = new AtomicBoolean();
        valid.set(true);
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        cache = cacheBuilder.build(new CacheLoader<String, Object>() {
            @Override
            public Object load(String key) throws Exception {
                return bankInfoService.queryBankInfo(key);
            }
        });
    }

    @Override
    public Object get(String key) {
        Object target = null;
        if(valid.get()){
            try {
                target = cache.get(key);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return target;
    }

    @Override
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    @Override
    public void invalidCache() {
        valid.set(false);
    }

    @Override
    public void validCache() {
        valid.set(true);
    }
}
