package com.veli.vshop.seckill.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 * @date 2021-07-15 22:43
 */
public class RateLimiterTest {
    private static final RateLimiter LIMITER = RateLimiter.create(10);
    private Cache<String, String> guavaCache;

    @Before
    public void before() {
        guavaCache = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build();
    }

    @Test
    public void test01() {
        for (int i = 1; i <= 1000; i++) {
            final int fInt = i % 100;
            new Thread(() -> getOpenApiData("key_" + fInt)).start();
        }
    }

    private void getOpenApiData(String key) {
        if (LIMITER.tryAcquire(10, TimeUnit.MILLISECONDS)) {
            System.out.println(key + openApi());
        } else {
            String val = guavaCache.getIfPresent(key);
            if (StringUtils.isBlank(val)) {
                val = openApi();
                guavaCache.put(key, val);
                System.out.println(key + val);
                return;
            }
            System.out.println(key + " hit cache: " + val);
        }
    }

    private String openApi() {
        try {
            // 模拟接口耗时
            TimeUnit.MILLISECONDS.sleep(50);
            return " --- openApi data ---";
        } catch (Exception ignore) {
        }
        return null;
    }
}

