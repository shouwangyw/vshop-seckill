package com.veli.vshop.seckill.queue.disruptor;

import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;

/**
 * 使用translator方式生产者
 * @author yangwei
 */
public class SeckillEventProducer {
    private static final EventTranslatorVararg<SeckillEvent> TRANSLATOR = (event, seq, args) -> {
        event.setSeckillId((Long) args[0]);
        event.setUserId((String) args[1]);
    };
    private final RingBuffer<SeckillEvent> ringBuffer;

    public SeckillEventProducer(RingBuffer<SeckillEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void seckill(long seckillId, String userId) {
        this.ringBuffer.publishEvent(TRANSLATOR, seckillId, userId);
    }
}
