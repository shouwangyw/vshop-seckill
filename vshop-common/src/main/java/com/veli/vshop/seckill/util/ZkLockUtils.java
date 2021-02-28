package com.veli.vshop.seckill.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 */
@Slf4j
public class ZkLockUtils {
    private static volatile CuratorFramework CLIENT;

    private ZkLockUtils() {
    }

    public static CuratorFramework init(String address) {
        if (CLIENT == null) {
            synchronized (ZkLockUtils.class) {
                if (CLIENT == null) {
                    start(address);
                }
            }
        }
        return CLIENT;
    }

    private static void start(String address) {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CLIENT = CuratorFrameworkFactory.builder()
                .connectString(address)
                .sessionTimeoutMs(15000)
                .connectionTimeoutMs(10000)
                .retryPolicy(retryPolicy)
                .build();
        CLIENT.start();
    }

    /**
     * 针对一件商品实现，多件商品同时秒杀建议实现一个map
     */
    private static class Holder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static InterProcessMutex MUTEX = new InterProcessMutex(CLIENT, "/curator/lock");
    }

    public static InterProcessMutex getMutex() {
        return Holder.MUTEX;
    }

    /**
     * 获得锁
     */
    public static boolean acquire(long time, TimeUnit unit) {
        try {
            return getMutex().acquire(time, unit);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 释放锁
     */
    public static void release() {
        try {
            getMutex().release();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
