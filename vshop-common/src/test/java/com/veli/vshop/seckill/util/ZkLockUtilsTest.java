package com.veli.vshop.seckill.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 * @date 2021-02-28 15:06
 */
@Slf4j
public class ZkLockUtilsTest {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(5);
    private static final int LOOP_COUNT = 10;
    private volatile int counter = 0;

    @Before
    public void before() {

    }

    @Test
    public void testZkLock() throws Exception {
        ZkLockUtils.init("192.168.254.120:2181");

        for (int i = 0; i < LOOP_COUNT; i++) {
            final int n = i;
            EXECUTOR.submit(() -> {
                boolean result = ZkLockUtils.acquire(5, TimeUnit.SECONDS);
                try {
                    counter++;
                    Thread currentThread = Thread.currentThread();
                    if (result) {
                        log.info(currentThread.getId() + " 获得锁成功: " + (n + 1));
                    } else {
                        log.info(currentThread.getId() + " 获得锁失败: " + (n + 1));
                    }
                    TimeUtils.sleepSec(4);
                } finally {
                    if (result) {
                        ZkLockUtils.release();
                    }
                }
            });
        }
        while (counter < LOOP_COUNT) {
            TimeUtils.sleepSec(5);
        }
    }
}
