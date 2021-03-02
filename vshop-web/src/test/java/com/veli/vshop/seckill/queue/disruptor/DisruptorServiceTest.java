package com.veli.vshop.seckill.queue.disruptor;

import com.veli.vshop.seckill.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author yangwei
 * @date 2021-03-01 21:29
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DisruptorServiceTest {
    private static final int LOOP_COUNT = 10;
    private volatile int counter = 0;

    @Resource
    private DisruptorService disruptorService;

//    @Before
//    public void before() {
//        disruptorService = new DisruptorService();
//    }

    @Test
    public void testSend() {
        for (int i = 1; i <= LOOP_COUNT; i++) {
            disruptorService.sendSeckillEvent(new SeckillEvent(i, String.valueOf(i)));
            counter++;
        }

        while (counter < LOOP_COUNT) {
            TimeUtils.sleepSec(5);
        }
    }
}
