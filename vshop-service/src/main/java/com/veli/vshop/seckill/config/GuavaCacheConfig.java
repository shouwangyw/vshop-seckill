package com.veli.vshop.seckill.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 * @date 2021-02-21 15:59
 */
@Configuration
public class GuavaCacheConfig {
    /**
     * 定义一个GuavaCache对象
     */
    private Cache<String, Object> guavaCache = null;

    @PostConstruct
    public void init() {
        guavaCache = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .maximumSize(100)
                // 设置缓存写入后过期时间
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build();
    }

    @Bean
    public Cache<String, Object> getGuavaCache() {
        return guavaCache;
    }
}
