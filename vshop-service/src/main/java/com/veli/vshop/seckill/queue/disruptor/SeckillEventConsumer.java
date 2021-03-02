package com.veli.vshop.seckill.queue.disruptor;

import com.lmax.disruptor.EventHandler;
import com.veli.vshop.seckill.exception.SeckillOrderException;
import com.veli.vshop.seckill.order.SeckillOrderService;
import com.veli.vshop.seckill.util.JsonUtils;
import com.veli.vshop.seckill.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 消费者(秒杀处理器)
 *
 * @author yangwei
 */
@Slf4j
public class SeckillEventConsumer implements EventHandler<SeckillEvent> {

    @Override
    public void onEvent(SeckillEvent event, long seq, boolean bool) throws Exception {
        log.debug("receive event: {}", JsonUtils.toStr(event));
        SeckillOrderService service = SpringUtils.getBean(SeckillOrderService.class);
        try {
            service.generalKilled(event.getSeckillId(), event.getUserId());
        } catch (SeckillOrderException e) {
            log.debug(e.getMsg());
        }
    }
}
