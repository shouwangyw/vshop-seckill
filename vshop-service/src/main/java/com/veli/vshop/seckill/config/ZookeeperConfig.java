package com.veli.vshop.seckill.config;

import com.veli.vshop.seckill.util.ZkLockUtils;
import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangwei
 * @date 2021-02-28 14:59
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperConfig {
    private String address;

    @Bean
    public CuratorFramework zkLockUtils() {
        return ZkLockUtils.init(address);
    }
}
