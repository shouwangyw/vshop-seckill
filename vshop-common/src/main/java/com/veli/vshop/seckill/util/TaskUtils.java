package com.veli.vshop.seckill.util;

import java.util.concurrent.*;

/**
 * @author yangwei
 * @date 2021-03-06 19:08
 */
public class TaskUtils {
    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(
            4,
            20,
            100,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2000));

    private TaskUtils() {}

    public static Future<Object> submit(Callable<Object> callable) {
        return EXECUTOR.submit(callable);
    }
}
