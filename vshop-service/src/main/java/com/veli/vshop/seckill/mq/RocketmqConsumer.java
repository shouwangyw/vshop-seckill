package com.veli.vshop.seckill.mq;

import com.veli.vshop.seckill.config.RocketmqConfig;
import com.veli.vshop.seckill.dao.mapper.TbSeckillGoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yangwei
 */
@Slf4j
@Component
public class RocketmqConsumer {
    @Resource
    private TbSeckillGoodsMapper seckillGoodsMapper;
    @Resource
    private RocketmqConfig rocketmqConfig;

    @Bean
    public DefaultMQPushConsumer seckillMqConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketmqConfig.getGroupName());
        consumer.setNamesrvAddr(rocketmqConfig.getNamesrvAddr());
        try {
            // 广播模式消费
            consumer.subscribe(rocketmqConfig.getTopic(), "*");
            // 如果是第一次启动，从队列头部开始消费；如果不是第一次启动，从上次消费的位置继续消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener((MessageListenerConcurrently) (messages, context) -> {
                try {
                    for (MessageExt messageExt : messages) {
                        String message = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                        // 同步数据库的库存
                        seckillGoodsMapper.updateByPrimaryKeyWithLock(Long.valueOf(message));
                        log.info("[Consumer] msgID: {}, msgBody: {}", messageExt.getMsgId(), message);
                    }
                } catch (Exception e) {
                    // 如果出现异常，必须告知消息进行重试
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
            log.info("[Consumer] Started ...");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return consumer;
    }
}
