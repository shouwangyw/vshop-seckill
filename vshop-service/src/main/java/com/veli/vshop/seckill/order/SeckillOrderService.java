package com.veli.vshop.seckill.order;

/**
 * @author yangwei
 * @date 2021-02-27 09:58
 */
public interface SeckillOrderService {
    /**
     * 普通秒杀
     * @param id        秒杀商品的主键ID
     */
    boolean generalKilled(Long id, String userId);
    /**
     * 加锁方式的 普通秒杀
     */
    boolean generalKilledByLock(Long id, String userId);
    /**
     * 数据库悲观锁下单，在分布式模式下控制库存
     */
    boolean sqlLockKilled(Long id, String userId);
    /**
     * 数据库乐观锁下单，在分布式模式下控制库存
     */
    boolean versionLockKilled(Long id, String userId);
    /**
     * Redis分布式锁下单，在分布式模式下控制库存
     */
    boolean redisLockKilled(Long id, String userId);
}
