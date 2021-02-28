package com.veli.vshop.seckill.distributedlock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁Demoredis分布式锁Demo
 *
 * @author yangwei
 */
@Slf4j
public class RedissonLockDemo {
    /**
     * 可重入锁（Reentrant Lock）
     * Redisson 的分布式可重入锁RLock Java对象实现了java.util.concurrent.locks.Lock接口，同时还支持自动过期解锁
     */
    public void testReentrantLock(RedissonClient redissonClient) {
        RLock lock = redissonClient.getLock("anyLock");
        try {
//            // 1、最常使用的方法
//            lock.lock();
//            // 2、支持过期解锁功能：10秒以后自动解锁，无需调用unlock方法手动解锁
//            lock.lock(10, TimeUnit.SECONDS);
            // 3、尝试加锁：最多等3秒，上锁以后10秒自动解锁
            boolean result = lock.tryLock(3, 10, TimeUnit.SECONDS);
            if (result) {
                // do your business
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Redisson同时还为分布式锁提供了异步执行的相关方法
     */
    public void testAsyncReentrantLock(RedissonClient redissonClient) {
        RLock lock = redissonClient.getLock("anyLock");
        try {
//            lock.lockAsync();
//            lock.lockAsync(10, TimeUnit.SECONDS);
            Future<Boolean> result = lock.tryLockAsync(3, 10, TimeUnit.SECONDS);
            if (result.get()) {
                // do your business
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 公平锁（Fair Lock）
     * Redisson分布式可重入公平锁也是实现了java.util.concurrent.locks.Lock接口的一种RLock对象。
     * 在提供了自动过期解锁功能的同时，保证了当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程。
     */
    public void testFairLock(RedissonClient redissonClient) {
        RLock fairLock = redissonClient.getFairLock("anyLock");
        try {
//            // 1、最常见的使用方法
//            fairLock.lock();
//            // 2、支持过期解锁功能：10秒以后自动解锁，无需调用unlock方法手动解锁
//            fairLock.lock(10, TimeUnit.SECONDS);
//            // 3、尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean result = fairLock.tryLock(100, 10, TimeUnit.SECONDS);
            if (result) {
                // do your business
            }
//            Redisson同时还为分布式可重入公平锁提供了异步执行的相关方法：
//            fairLock.lockAsync();
//            fairLock.lockAsync(10, TimeUnit.SECONDS);
//            Future<Boolean> result = fairLock.tryLockAsync(100, 10, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            fairLock.unlock();
        }
    }

    /**
     * 联锁（MultiLock）
     * edisson的RedissonMultiLock对象可以将多个RLock对象关联为一个联锁，每个RLock对象实例可以来自于不同的Redisson实例
     */
    public void testMultiLock(RedissonClient client1, RedissonClient client2, RedissonClient client3) {
        RLock lock1 = client1.getLock("lock1");
        RLock lock2 = client2.getLock("lock2");
        RLock lock3 = client3.getLock("lock3");
        RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);
        try {
//            // 同时加锁：lock1 lock2 lock3, 所有的锁都上锁成功才算成功。
//            lock.lock();
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean result = lock.tryLock(100, 10, TimeUnit.SECONDS);
            if (result) {
                // do your business
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 红锁（RedLock）
     * Redisson的RedissonRedLock对象实现了Redlock介绍的加锁算法。
     * 该对象也可以用来将多个RLock对象关联为一个红锁，每个RLock对象实例可以来自于不同的Redisson实例
     */
    public void testRedLock(RedissonClient client1, RedissonClient client2, RedissonClient client3) {
        RLock lock1 = client1.getLock("lock1");
        RLock lock2 = client2.getLock("lock2");
        RLock lock3 = client3.getLock("lock3");
        RedissonRedLock lock = new RedissonRedLock(lock1, lock2, lock3);
        try {
//            // 同时加锁：lock1 lock2 lock3, 所有的锁都上锁成功才算成功。
//            lock.lock();
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean result = lock.tryLock(100, 10, TimeUnit.SECONDS);
            if (result) {
                // do your business
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }

    //读写锁（ReadWriteLock）、信号量（Semaphore）、可过期性信号量（PermitExpirableSemaphore）、闭锁（CountDownLatch）
}
