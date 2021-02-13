package com.veli.vshop.seckill.util;

import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 */
public class TimeUtils {
    private TimeUtils() {}

    public static void sleepSec(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception ignored) {
        }
    }
}
