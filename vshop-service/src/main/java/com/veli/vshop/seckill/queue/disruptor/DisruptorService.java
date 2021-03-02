package com.veli.vshop.seckill.queue.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;

/**
 * @author yangwei
 */
@Slf4j
@Component
public class DisruptorService implements AutoCloseable {
    static Disruptor<SeckillEvent> disruptor;

    static {
        SeckillEventFactory factory = new SeckillEventFactory();
        ThreadFactory threadFactory = Thread::new;
        disruptor = new Disruptor<>(factory, 1024, threadFactory);
        disruptor.handleEventsWith(new SeckillEventConsumer());
        disruptor.start();
    }

    public static void sendSeckillEvent(SeckillEvent event) {
        RingBuffer<SeckillEvent> ringBuffer = disruptor.getRingBuffer();
        SeckillEventProducer producer = new SeckillEventProducer(ringBuffer);
        producer.seckill(event.getSeckillId(), event.getUserId());
    }

    @Override
    public void close() throws Exception {
        disruptor.shutdown();
    }
}
