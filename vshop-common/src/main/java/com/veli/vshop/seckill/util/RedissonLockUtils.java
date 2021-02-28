package com.veli.vshop.seckill.util;

import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 */
public class RedissonLockUtils {
    private static volatile RedissonClient CLIENT;

    private RedissonLockUtils() {
    }

    public RedissonLockUtils(RedissonClient client) {
        if (CLIENT == null) {
            synchronized (this) {
                if (CLIENT == null) {
                    CLIENT = client;
                }
            }
        }
    }

    /**
     * 加锁
     */
    public static RLock lock(String key) {
        RLock lock = CLIENT.getLock(key);
        lock.lock();
        return lock;
    }

    /**
     * 释放锁
     */
    public static void unlock(String key) {
        RLock lock = CLIENT.getLock(key);
        lock.unlock();
    }

    /**
     * 释放锁
     */
    public static void unlock(RLock lock) {
        lock.unlock();
    }

    /**
     * 带超时的锁
     */
    public static RLock lock(String key, int timeout) {
        return lock(key, timeout, TimeUnit.SECONDS);
    }

    public static RLock lock(String key, int timeout, TimeUnit unit) {
        RLock lock = CLIENT.getLock(key);
        lock.lock(timeout, unit);
        return lock;
    }

    /**
     * 尝试获取锁
     */
    public static boolean tryLock(String key, int waitTime, int leaseTime) {
        return tryLock(key, waitTime, leaseTime, TimeUnit.SECONDS);
    }

    public static boolean tryLock(String key, int waitTime, int leaseTime, TimeUnit unit) {
        RLock lock = CLIENT.getLock(key);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 初始红包数量
     */
    public void initCount(String key, int count) {
        RMapCache<String, Integer> mapCache = CLIENT.getMapCache("skill");
        mapCache.putIfAbsent(key, count, 3, TimeUnit.DAYS);
    }

    /**
     * 递增
     */
    public int incr(String key, int delta) {
        RMapCache<String, Integer> mapCache = CLIENT.getMapCache("skill");
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        // 加1并获取计算后的值
        return mapCache.addAndGet(key, delta);
    }

    /**
     * 递减
     */
    public int decr(String key, int delta) {
        RMapCache<String, Integer> mapCache = CLIENT.getMapCache("skill");
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        // 加1并获取计算后的值
        return mapCache.addAndGet(key, -delta);
    }
}
