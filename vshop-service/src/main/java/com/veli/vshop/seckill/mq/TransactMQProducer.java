package com.veli.vshop.seckill.mq;

import com.veli.vshop.seckill.config.RocketmqConfig;
import com.veli.vshop.seckill.dao.entity.TbSeckillGoods;
import com.veli.vshop.seckill.dao.mapper.TbSeckillGoodsMapper;
import com.veli.vshop.seckill.domain.SeckillDto;
import com.veli.vshop.seckill.exception.SeckillOrderException;
import com.veli.vshop.seckill.order.SeckillOrderService;
import com.veli.vshop.seckill.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
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
public class TransactMQProducer {
    private TransactionMQProducer producer = new TransactionMQProducer();
    @Resource
    private RocketmqConfig rocketmqConfig;
    @Resource
    private SeckillOrderService seckillOrderService;
    @Resource
    private TbSeckillGoodsMapper seckillGoodsMapper;

    @PostConstruct
    public void init() {
        try {
            producer.setProducerGroup(rocketmqConfig.getGroupName());
            producer.setNamesrvAddr(rocketmqConfig.getNamesrvAddr());
            producer.setRetryTimesWhenSendFailed(rocketmqConfig.getRetryTimes());
            producer.setVipChannelEnabled(false);
            producer.start();
            addListener();
            log.info("[Producer] Started ...");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void addListener() {
        // 注入一个监听器
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 执行本地业务的方法
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                Long seckillId = 0L;
                try {
                    // 获取消息内容
                    String msg = new String(message.getBody(), RemotingHelper.DEFAULT_CHARSET);
                    SeckillDto seckillDto = JsonUtils.toObj(msg, SeckillDto.class);
                    if (seckillDto != null) {
                        seckillId = seckillDto.getSeckillId();
                        seckillOrderService.redisCacheKilled(seckillDto.getSeckillId(), seckillDto.getUserId());
                    }
                } catch (SeckillOrderException e) {
                    // 业务处理中出现一个预知的异常，设置事务回滚状态
                    TbSeckillGoods updateObj = new TbSeckillGoods()
                            .setId(Integer.parseInt(seckillId.toString()))
                            .setTransactionStatus(-1);
                    seckillGoodsMapper.updateByPrimaryKeySelective(updateObj);
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return LocalTransactionState.UNKNOW;
                }
                // 业务执行成功，确定事务提交状态
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            /**
             * 事务状态回查方法
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                try {
                    // 获取消息内容
                    String msg = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                    SeckillDto seckillDto = JsonUtils.toObj(msg, SeckillDto.class);
                    // 查询事务状态
                    TbSeckillGoods seckillGoods = seckillGoodsMapper.selectByPrimaryKey(seckillDto.getSeckillId());
                    // 根据事务状态，判定事务 commit, rollback, unkown
                    switch (seckillGoods.getTransactionStatus()) {
                        case -1:
                            return LocalTransactionState.ROLLBACK_MESSAGE;
                        case 1:
                            return LocalTransactionState.COMMIT_MESSAGE;
                        default:
                            return LocalTransactionState.UNKNOW;
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
    }

    /**
     * 发送消息，使用事务型消息把所有的操作原子化
     */
    public boolean sendTransactionMsg(Long seckillId, String userId) {
        try {
            SeckillDto seckillDto = new SeckillDto(seckillId, userId);
            byte[] bytes = JsonUtils.toStr(seckillDto).getBytes(RemotingHelper.DEFAULT_CHARSET);
            Message message = new Message(rocketmqConfig.getTopic(), bytes);
            producer.sendMessageInTransaction(message, null);
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
