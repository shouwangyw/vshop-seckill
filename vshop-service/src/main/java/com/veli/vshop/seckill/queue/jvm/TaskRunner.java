package com.veli.vshop.seckill.queue.jvm;

import com.veli.vshop.seckill.dao.entity.TbSeckillOrder;
import com.veli.vshop.seckill.order.SeckillOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yangwei
 */
@Slf4j
@Component
public class TaskRunner implements ApplicationRunner {
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    @Resource
    private SeckillOrderService seckillOrderService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 提交一个任务，一直监听bockingQueue队列
        EXECUTOR.submit(() -> {
            log.info("==>> TaskRunner started");
            while (true) {
                try {
                    TbSeckillOrder order = SeckillQueue.getInstance().consume();
                    if (order != null) {
                        // 从队列中获取订单，执行下单操作
                        log.debug("execute ordering...");
                        seckillOrderService.ordering(order.getSeckillId(), order.getUserId());
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        });
    }
}
