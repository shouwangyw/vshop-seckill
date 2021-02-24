package com.veli.vshop.seckill.order;

import com.veli.vshop.seckill.dao.entity.TbSeckillGoods;
import com.veli.vshop.seckill.domain.BasePageInfo;

/**
 * @author yangwei
 * @date 2021-02-13 20:15
 */
public interface SeckillGoodsService {
    /**
     * 新增一条
     */
    boolean saveOne(TbSeckillGoods tbSeckillGoods);
    /**
     * 分页查询商品信息
     */
    BasePageInfo<TbSeckillGoods> queryGoodsByPage(Integer pageNum, Integer pageSize);
    /**
     * 根据id查询商品详情信息
     */
    TbSeckillGoods queryGoodsDetails(Integer id);
    /**
     * 根据id查询商品详情信息
     */
    TbSeckillGoods queryGoodsDetailsByCache(Integer id);
}
