package com.veli.vshop.seckill.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 * @date 2020-10-11 23:26
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig implements Serializable {
    public static final String CACHE_10_SEC = "CACHE_10_SEC";
    public static final String CACHE_1_HOUR = "CACHE_1_HOUR";

    //-- region -- RedisTemplate

    /******************************* RedisTemplate *******************************/
    @Bean
    <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        settingSerializer(redisTemplate);

        return redisTemplate;
    }

    @Bean
    RedisTemplate<String, Object> objRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        settingSerializer(redisTemplate);

        return redisTemplate;
    }

    private static ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
    private void settingSerializer(RedisTemplate<String, ?> redisTemplate) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        // jackson 序列化
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        // fastJson 序列化
//        FastJsonRedisSerializer<Object> redisSerializer = new FastJsonRedisSerializer<>(Object.class);
//        redisTemplate.setValueSerializer(redisSerializer);
//        redisTemplate.setHashValueSerializer(redisSerializer);
//
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringRedisSerializer);
//        redisTemplate.setHashKeySerializer(stringRedisSerializer);
    }

    @Bean
    @Primary
    RedisTemplate<String, Integer> intRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        settingSerializer(redisTemplate);

        return redisTemplate;
    }

    @ConditionalOnMissingBean
    @Bean("stringRedisTemplate")
    RedisTemplate<String, String> strRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        settingSerializer(redisTemplate);

        return redisTemplate;
    }

    @Bean
    RedisTemplate<String, Long> longRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        settingSerializer(redisTemplate);

        return redisTemplate;
    }

    @Bean
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(ObjectMapper objectMapper) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
                Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }
    //-- endregion -- RedisTemplate

    //-- region -- 缓存管理器

    /******************************************** 缓存管理器 ********************************************/
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                // 默认策略，未配置的 key 会使用这个
                this.getRedisCacheConfigurationWithTtl(60),
                // 指定 key 策略
                this.getRedisCacheConfigurationMap()
        );
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>(2);
        redisCacheConfigurationMap.put(CACHE_1_HOUR, this.getRedisCacheConfigurationWithTtl(60 * 60));
        redisCacheConfigurationMap.put(CACHE_10_SEC, this.getRedisCacheConfigurationWithTtl(10));

        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));

        return redisCacheConfiguration;
    }
    //-- endregion -- 缓存管理器

    //-- region -- redisTemplate operation

    /******************************************** redisTemplate operation ********************************************/
    @Bean
    ValueOperations<String, String> opsForValue(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    ValueOperations<String, Integer> opsForValueInt(RedisTemplate<String, Integer> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    ValueOperations<String, Long> opsForValueLong(RedisTemplate<String, Long> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    ValueOperations<String, Object> opsForValueObj(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    ListOperations<String, String> opsForList(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    ZSetOperations<String, String> opsForZSet(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    @Bean
    SetOperations<String, String> opsForSet(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForSet();
    }

//    @Bean
//    SetOperations<String, Object> opsForSetObj(RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForSet();
//    }

    @Bean
    HashOperations<String, String, String> opsForHash(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForHash();
    }
    //-- endregion -- redisTemplate operation
}
