package com.veli.vshop.seckill.config;

import com.veli.vshop.seckill.util.RedissonLockUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangwei
 */
@Data
@Configuration
@ConditionalOnClass(Config.class)
@ConfigurationProperties(prefix = "redisson")
public class RedissonConfig {
    private int timeout = 3000;
    private String address;
    private String password;
    private int connectionPoolSize = 64;
    private int connectionMinimumIdleSize = 10;
    private int slaveConnectionPoolSize = 250;
    private int masterConnectionPoolSize = 250;
    private String[] sentinelAddresses;
    private String masterName;

//    /**
//     * 哨兵模式自动装配
//     */
//    @Bean
//    @ConditionalOnProperty(name = "redisson.master-name")
//    public RedissonClient sentinelRedissonClient() {
//        Config config = new Config();
//        SentinelServersConfig serversConfig = config.useSentinelServers()
//                .addSentinelAddress(getSentinelAddresses())
//                .setMasterName(getMasterName())
//                .setTimeout(getTimeout())
//                .setMasterConnectionPoolSize(getMasterConnectionPoolSize())
//                .setSlaveConnectionPoolSize(getSlaveConnectionPoolSize());
//        if (StringUtils.isNotBlank(getPassword())) {
//            serversConfig.setPassword(getPassword());
//        }
//        return Redisson.create(config);
//    }

    /**
     * 单机模式自动装配
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.address")
    public RedissonClient singleRedissonClient() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(getAddress())
                .setTimeout(getTimeout())
                .setConnectionPoolSize(getConnectionPoolSize())
                .setConnectionMinimumIdleSize(getConnectionMinimumIdleSize());
        if (StringUtils.isNotBlank(getPassword())) {
            serverConfig.setPassword(getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     */
    @Bean
    public RedissonLockUtils redissonLockUtils(RedissonClient redissonClient) {
        return new RedissonLockUtils(redissonClient);
    }
}
