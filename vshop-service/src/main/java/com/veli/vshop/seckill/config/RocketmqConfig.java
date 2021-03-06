package com.veli.vshop.seckill.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangwei
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "rocketmq")
public class RocketmqConfig {
    private String namesrvAddr;
    private String groupName;
    private String topic;
    private int retryTimes;
}
