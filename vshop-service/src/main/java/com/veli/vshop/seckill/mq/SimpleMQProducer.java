package com.veli.vshop.seckill.mq;

import com.veli.vshop.seckill.config.RocketmqConfig;
import com.veli.vshop.seckill.domain.SeckillDto;
import com.veli.vshop.seckill.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author yangwei
 */
@Slf4j
@Component
public class SimpleMQProducer {
    private DefaultMQProducer producer = new DefaultMQProducer();

    @Resource
    private RocketmqConfig rocketmqConfig;

    @PostConstruct
    public void init() {
        try {
            producer.setProducerGroup(rocketmqConfig.getGroupName());
            producer.setNamesrvAddr(rocketmqConfig.getNamesrvAddr());
            producer.setRetryTimesWhenSendFailed(rocketmqConfig.getRetryTimes());
            producer.setVipChannelEnabled(false);
            producer.start();
            log.info("[Producer] Started ...");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 发送同步数据库库存的消息
     */
    public boolean sendSyncStockMsg(Long seckillId) {
        try {
            SeckillDto seckillDto = new SeckillDto().setSeckillId(seckillId);
            byte[] bytes = JsonUtils.toStr(seckillDto).getBytes(RemotingHelper.DEFAULT_CHARSET);
            Message message = new Message(rocketmqConfig.getTopic(), bytes);
            SendResult result = producer.send(message);
            log.info("==>> SendResult: {}", JsonUtils.toStr(result));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @PreDestroy
    public void destroy() {
        if (producer != null) {
            producer.shutdown();
        }
    }
}
