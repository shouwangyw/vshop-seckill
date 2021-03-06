package com.veli.vshop.seckill.domain;

/**
 * @author yangwei
 * @date 2021-02-11 16:55
 */
public interface CommonConstants{
    String AUTH_TOKEN = "Authorization";

    /**
     * 秒杀商品相关
     */
    // 秒杀商品分布式锁
    String SEC_KILL_GOODS_LOCK_PREFIX = "seckill_goods_lock_";
    // 秒杀商品
    String SEC_KILL_GOODS_CACHE_PREFIX = "seckill_goods_";
    // 秒杀商品库存
    String SEC_KILL_GOODS_STOCK_CACHE_PREFIX = "seckill_goods_stock_";
    // 秒杀商品售罄
    String SEC_KILL_GOODS_STOCK_END_CACHE_PREFIX = "seckill_goods_stock_end_";

}
