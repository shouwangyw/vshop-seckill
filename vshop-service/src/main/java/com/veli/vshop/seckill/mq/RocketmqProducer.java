package com.veli.vshop.seckill.mq;

import com.alibaba.fastjson.JSON;
import com.veli.vshop.seckill.config.RocketmqConfig;
import com.veli.vshop.seckill.dao.entity.TbSeckillGoods;
import com.veli.vshop.seckill.dao.mapper.TbSeckillGoodsMapper;
import com.veli.vshop.seckill.exception.SeckillOrderException;
import com.veli.vshop.seckill.order.SeckillOrderService;
import com.veli.vshop.seckill.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 * @date 2021-03-03 22:11
 */
@Slf4j
@Component
public class RocketmqProducer {
    private TransactionMQProducer transactionMQProducer;
    private DefaultMQProducer producer;
    @Resource
    private RocketmqConfig rocketmqConfig;
    @Resource
    private SeckillOrderService seckillOrderService;
    @Resource
    private TbSeckillGoodsMapper seckillGoodsMapper;

    @PostConstruct
    public void init() {
        producer = new TransactionMQProducer(rocketmqConfig.getGroupName());
        producer.setNamesrvAddr(rocketmqConfig.getNamesrvAddr());
        producer.setRetryTimesWhenSendFailed(rocketmqConfig.getRetryTimes());
        try {
            // 注入一个监听器
//            producer.setTransactionListener(new TransactionListener() {
//                /**
//                 * 执行本地业务的方法
//                 */
//                @Override
//                public LocalTransactionState executeLocalTransaction(Message message, Object o) {
//                    Integer seckillId = 0;
//                    try {
//                        String msg = new String(message.getBody(), RemotingHelper.DEFAULT_CHARSET);
//                        // 获取消息内容
//                        Map<String, String> maps = JSON.parseObject(msg, Map.class);
//                        seckillId = Integer.parseInt(maps.get("seckillId"));
//                        String userId = maps.get("userId");
//                        seckillOrderService.redisCacheKilled((long) seckillId, userId);
//                    } catch (SeckillOrderException e) {
//                        // 业务处理中出现一个预知的异常，设置事务回滚状态
//                        TbSeckillGoods updateObj = new TbSeckillGoods()
//                                .setId(seckillId)
//                                .setTransactionStatus(-1);
//                        seckillGoodsMapper.updateByPrimaryKeySelective(updateObj);
//                        return LocalTransactionState.ROLLBACK_MESSAGE;
//                    } catch (Exception e) {
//                        log.error(e.getMessage(), e);
//                        return LocalTransactionState.UNKNOW;
//                    }
//                    // 业务执行成功，确定事务提交状态
//                    return LocalTransactionState.COMMIT_MESSAGE;
//                }
//
//                /**
//                 * 事务状态回查方法
//                 */
//                @Override
//                public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
//                    try {
//                        String msg = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
//                        // 获取消息内容
//                        Map<String, String> maps = JSON.parseObject(msg, Map.class);
//                        Integer seckillId = Integer.parseInt(maps.get("seckillId"));
//                        // 查询事务状态
//                        TbSeckillGoods seckillGoods = seckillGoodsMapper.selectByPrimaryKey(seckillId);
//                        // 根据事务状态，判定事务 commit, rollback, unkown
//                        switch (seckillGoods.getTransactionStatus()) {
//                            case -1:
//                                return LocalTransactionState.ROLLBACK_MESSAGE;
//                            case 1:
//                                return LocalTransactionState.COMMIT_MESSAGE;
//                            default:
//                                return LocalTransactionState.UNKNOW;
//                        }
//                    } catch (Exception e) {
//                        log.error(e.getMessage(), e);
//                    }
//                    return LocalTransactionState.COMMIT_MESSAGE;
//                }
//            });
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
            byte[] bytes = String.valueOf(seckillId).getBytes(RemotingHelper.DEFAULT_CHARSET);
            SendResult result = producer.send(new Message(rocketmqConfig.getTopic(), bytes));
            log.info("==>> SendResult: {}", JsonUtils.toStr(result));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 发送消息，使用事务型消息把所有的操作原子化
     */
    public boolean sendTranscationMsg(Long seckillId, String userId) {
        Map<String, String> map = new HashMap<>(2);
        map.put("seckillId", String.valueOf(seckillId));
        map.put("userId", userId);

        try {
            byte[] bytes = JsonUtils.toStr(map).getBytes(RemotingHelper.DEFAULT_CHARSET);
            transactionMQProducer.sendMessageInTransaction(new Message(rocketmqConfig.getTopic(), bytes), null);
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
        if (transactionMQProducer != null) {
            transactionMQProducer.shutdown();
        }
    }
}
